package com.tbadhit.submissionmade1.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.tbadhit.core.data.Resource
import com.tbadhit.core.domain.model.User
import com.tbadhit.submissionmade1.R
import com.tbadhit.submissionmade1.adapter.SectionsPagerAdapter
import com.tbadhit.submissionmade1.databinding.ActivityDetailBinding
import com.tbadhit.submissionmade1.transform.ZoomOutPageTransformer
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()
    private var isFavorite = false
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        supportActionBar?.title = username

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            detailViewModel.getDetailUser(username).observe(this) {
                when (it) {
                    is Resource.Success -> {
                        user = it.data!!
                        binding.apply {
                            Glide.with(this@DetailActivity)
                                .load(user.avatarUrl)
                                .apply(
                                    RequestOptions.circleCropTransform()
                                        .placeholder(R.drawable.ic_account_circle)
                                )
                                .into(imgItemPhoto)

                            tvName.text = user.name ?: getString(R.string.name)
                            tvUsername.text = user.username
                            tvLocation.text = user.location ?: "-"
                            tvCompany.text = user.company ?: "-"
                            tvRepository.text = user.publicRepos.toString()
                            tvFollowers.text = user.followers.toString()
                            tvFollowing.text = user.following.toString()
                        }

                        detailViewModel.checkUser(id)?.observe(this@DetailActivity) { userLocal ->
                            isFavorite = userLocal.isFavorite == true
                            setStatusFavorite(isFavorite)
                        }

                        binding.floatingActionButton.show()
                    }
                    is Resource.Error -> binding.floatingActionButton.hide()
                    is Resource.Loading -> binding.floatingActionButton.hide()
                }
                setStatusFavorite(isFavorite)
                binding.floatingActionButton.setOnClickListener {
                    addOrRemoveFavorite()
                    setStatusFavorite(isFavorite)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            viewPager.setPageTransformer(ZoomOutPageTransformer())
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TITLE_TABS[position])
            }.attach()
        }
    }

    private fun addOrRemoveFavorite() {
        if (!isFavorite) {
            user.isFavorite = !isFavorite
            detailViewModel.addToFavorite(
                user.username,
                user.id,
                user.avatarUrl,
                user.isFavorite
            )
            Toast.makeText(
                this@DetailActivity,
                getString(R.string.toast_add_favorite),
                Toast.LENGTH_SHORT
            ).show()
            isFavorite = !isFavorite
        } else {
            user.isFavorite = !isFavorite
            detailViewModel.removeFromFavorite(user)
            isFavorite = !isFavorite
            Toast.makeText(
                this@DetailActivity,
                getString(R.string.toast_remove_favorite),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun setStatusFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_white)
        } else {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        private val TITLE_TABS = intArrayOf(R.string.followers, R.string.following)
    }
}