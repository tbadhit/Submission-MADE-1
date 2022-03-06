package com.tbadhit.submissionmade1.follower

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbadhit.core.data.Resource
import com.tbadhit.submissionmade1.detail.DetailActivity
import com.tbadhit.core.ui.ListUserAdapter
import com.tbadhit.submissionmade1.databinding.FragmentFollowerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val followerViewModel: FollowerViewModel by viewModel()
    private lateinit var adapterFollower: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        adapterFollower = ListUserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }
            startActivity(intent)
        }
        binding.rvFollower.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollower
        }

        showLoading(true)

        followerViewModel.setFollower(username)

        observeFollow()
    }

    private fun observeFollow() {
        followerViewModel.userFollowers.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is Resource.Success ->
                        if (!it.data.isNullOrEmpty()) {
                            showLoading(false)
                            adapterFollower.run { setListUser(it.data) }
                        } else {
                            showLoading(false)
                        }
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> showLoading(false)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}