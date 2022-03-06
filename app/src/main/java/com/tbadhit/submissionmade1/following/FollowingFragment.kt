package com.tbadhit.submissionmade1.following

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
import com.tbadhit.submissionmade1.databinding.FragmentFollowingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel: FollowingViewModel by viewModel()
    private lateinit var adapterFollowing: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapterFollowing = ListUserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }
            startActivity(intent)
        }

        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollowing
        }

        showLoading(true)

        followingViewModel.setFollowing(username)
        observeFollowing()

    }

    private fun observeFollowing() {
        followingViewModel.userFollowing.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is Resource.Success ->
                        if (!it.data.isNullOrEmpty()) {
                            showLoading(false)
                            adapterFollowing.run { setListUser(it.data) }
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