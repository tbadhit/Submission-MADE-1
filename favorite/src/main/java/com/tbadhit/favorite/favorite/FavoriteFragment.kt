package com.tbadhit.favorite.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbadhit.core.ui.ListUserAdapter
import com.tbadhit.submissionmade1.R
import com.tbadhit.favorite.di.favoriteModule
import com.tbadhit.submissionmade1.databinding.FragmentFavoriteBinding
import com.tbadhit.submissionmade1.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteAdapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.favorite)
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimation.setAnimation("empty-favorite.json")

        binding.lottieAnimation.playAnimation()

        favoriteAdapter = ListUserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }

            startActivity(intent)
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        observeDetail()
    }

    private fun observeDetail() {
        favoriteViewModel.favoritesUsers.observe(viewLifecycleOwner) {
            it.let {
                if (!it.isNullOrEmpty()) {
                    favoriteAdapter.setListUser(it)
                    binding.lottieAnimation.visibility = View.GONE
                } else {
                    binding.rvFavorite.visibility = View.GONE
                    binding.lottieAnimation.visibility = View.VISIBLE
                }
            }
        }
    }

}