package com.example.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.data.response.Follow
import com.example.githubuser.ui.FollowViewModel
import com.example.githubuser.ui.adapter.FollowAdapter

class FollowFrag : Fragment() {
    private val binding by lazy { FragmentFollowBinding.inflate(layoutInflater) }
    private lateinit var followViewModel: FollowViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(KEY_USERNAME) ?: ""
        val position = arguments?.getInt(KEY_POSITION)

        observeViewModel()

        when (position) {
            0 -> followViewModel.getFollowerList(username)
            1 -> followViewModel.getFollowingList(username)
        }
    }
    private fun observeViewModel (){
        followViewModel = FollowViewModel()
        followViewModel.userFollow.observe(viewLifecycleOwner) { userFollow ->
            showFollow(userFollow)
        }
        followViewModel.loadingState.observe(viewLifecycleOwner){ loadingState ->
            showLoading(loadingState)
        }
    }
    fun showFollow(followList: List<Follow>?) {
        if (followList != null) {
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvFollow.layoutManager = layoutManager
            val adapter = FollowAdapter(followList)
            binding.rvFollow.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val KEY_POSITION = "KEY_POSITION"
        const val KEY_USERNAME = "KEY_USERNAME"
    }
}