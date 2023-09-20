package com.example.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.data.response.User
import com.example.githubuser.ui.adapter.GithubAdapter
import com.example.githubuser.ui.MainViewModel
import com.example.githubuser.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupSearchBar()

        observeViewModel()

        mainViewModel.findUser("rafly")
    }


    private fun observeViewModel() {
        mainViewModel = MainViewModel()
        mainViewModel.userList.observe(this) { userList ->
            showUser(userList)
        }
        mainViewModel.loadingState.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setupSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()

                    val searchUser = searchView.text.toString()
                    if (searchUser.isNotEmpty()) {
                        mainViewModel.findUser(searchUser)
                    }
                    false
                }
        }
    }

    private fun showUser(listUser: List<User>?) {
        if (listUser != null) {
            setupRecyclerView()

            val adapter = GithubAdapter(listUser)
            binding.rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    intentToDetail.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                    startActivity(intentToDetail)
                }
            })
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}