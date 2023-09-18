package ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityDetailBinding
import data.response.GithubDetail
import data.response.GithubResponse
import data.response.User
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
//        val nama = intent.getStringExtra(EXTA_NAMA)
//        val follower = intent.getStringExtra(EXTRA_FOLLOWER)
//        val following = intent.getStringExtra(EXTRA_FOLLOWING)
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

//    private fun showDetailUser (username: String) {
//        binding.tvUsername.text = username
}