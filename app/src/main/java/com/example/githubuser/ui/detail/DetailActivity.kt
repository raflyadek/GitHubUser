package com.example.githubuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavUser
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.example.githubuser.data.remote.response.GithubDetail
import com.example.githubuser.ui.viewmodel.DetailViewModel
import com.example.githubuser.ui.adapter.FollowPagerAdapter
import com.example.githubuser.ui.viewmodel.FavViewModel
import com.example.githubuser.ui.viewmodel.MainViewModel
import com.example.githubuser.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "EXTRA_DETAIL"
    }

    private lateinit var viewModel: DetailViewModel
    private var isFavUser: Boolean = false

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInsntance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val username = intent.getStringExtra(EXTRA_DETAIL) ?: ""
        initView(username)
        observeViewModel(username)
    }


    private fun initView(username: String){
        val followPagerAdapter = FollowPagerAdapter(this, username)
        binding.viewPager.adapter = followPagerAdapter

        val tabsTittle = arrayListOf("Followers", "Following")
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = tabsTittle[position]
        }.attach()
    }

    private fun observeViewModel(username: String){
        viewModel.getUserDetail(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                    Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        showDetailUser(result.data)
                    }
                }
            }
        }

        viewModel.favUser.observe(this) { favUsers ->
            isFavUser = favUsers.filter { it.username == username }.isNotEmpty()
            setupFavorite(isFavUser)
        }
    }


    private fun showDetailUser (data: GithubDetail){
        Glide.with(binding.root)
            .load(data.avatarUrl)
            .into(binding.imgProfile)
        binding.tvUsername.text = data.login
        binding.tvName.text = data.login
        binding.tvFollowersCount.text = data.followers.toString()
        binding.tvFollowingCount.text = data.following.toString()


        binding.fabFav.setOnClickListener{
            if (isFavUser) {
                viewModel.deleteFavorite(
                    FavUser(
                        username = data.login!!,
                        avatarUrl = data.avatarUrl,
                    )
                )
                Toast.makeText(this, "Removed to favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insertFavorite(
                    FavUser(
                        username = data.login!!,
                        avatarUrl = data.avatarUrl,
                    )
                )
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun setupFavorite(isFav: Boolean) {
        if (isFav) {
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fab_filled))
        } else {
            binding.fabFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fab))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}