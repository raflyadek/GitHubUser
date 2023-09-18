package ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import data.response.GithubDetail
import data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DETAIL = "EXTRA_DETAIL"
    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DETAIL) ?: ""
        Log.d("DetailActivity", "OnCreate: DATA USERNAME: $username")
        getDetailUser(username)
        initView(username)
        getDetailUser(username = username)
    }

    private fun initView(username: String){
        val followPagerAdapter = FollowPagerAdapter(this, username)
        binding.viewPager.adapter = followPagerAdapter

        val tabsTittle = arrayListOf("Followers", "Following")
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = tabsTittle[position]
        }.attach()

    }

    private fun getDetailUser(username: String) {
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<GithubDetail> {
            override fun onResponse(
                call: Call<GithubDetail>,
                response: Response<GithubDetail>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        showDetailUser(username)
                        showDetailUser(responseBody)
                    }
                } else {
                    Log.e("DetailActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubDetail>, t: Throwable) {
                Log.e("DetailActivity", "onFailure: ${t.message}")
            }
        })
    }

    private fun showDetailUser (data: GithubDetail){
        Glide.with(binding.root)
            .load(data.avatarUrl)
            .into(binding.imgProfile)
        binding.tvUsername.text = data.login
        binding.tvNamee.text = data.login
    }
}