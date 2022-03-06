package com.tbadhit.submissionmade1.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.tbadhit.submissionmade1.R
import com.tbadhit.submissionmade1.databinding.FragmentHomeBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbadhit.core.data.Resource
import com.tbadhit.core.ui.ListUserAdapter
import com.tbadhit.submissionmade1.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterUser: ListUserAdapter
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.appbar)
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimation.setAnimation("not-found.json")

        binding.lottieAnimation.playAnimation()

        adapterUser = ListUserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }
            startActivity(intent)
        }

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterUser
        }

        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchViewModel.setSearch(query)
                    binding.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        observeHome()
    }

    private fun observeHome() {
        searchViewModel.users.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let { data -> adapterUser.setListUser(data) }
                        binding.rvUser.visibility = View.VISIBLE
                        binding.lottieAnimation.visibility = View.GONE
                        resources
                    }
                    is Resource.Loading -> {
                        showLoading(true)
                        binding.rvUser.visibility = View.GONE
                        binding.lottieAnimation.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.lottieAnimation.visibility = View.VISIBLE
                        showLoading(false)
                    } // sementara
                }
            }
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_favoriteFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}