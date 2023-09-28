package com.example.githubuser.ui.insert.fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.databinding.ActivityFavBinding
import com.example.githubuser.ui.adapter.FavAdapter
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ui.viewmodel.FavViewModel
import com.example.githubuser.ui.viewmodel.ViewModelFactory

class FavActivity : AppCompatActivity() {
    val favViewModel: FavViewModel by viewModels {
        ViewModelFactory(application)
    }

    private val binding by lazy { ActivityFavBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        favViewModel.getFavUsers().observe(this) {
            showUser(it)
        }
        favViewModel.loadingState.observe(this){ isloading ->
            showLoading(isloading)
        }
    }
        private fun showUser(listUser: List<FavUser>?) {
            if (listUser != null) {
                setupRecyclerView()

                val adapter = FavAdapter(listUser)
                binding.rvUser.adapter = adapter
                adapter.setOnItemClickCallback(object : FavAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: FavUser) {
                        val intentToDetail = Intent(this@FavActivity, DetailActivity::class.java)
                        intentToDetail.putExtra(DetailActivity.EXTRA_DETAIL, data.username)
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
