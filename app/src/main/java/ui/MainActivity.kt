package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import data.response.GithubResponse
import data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        findUser()
    }
    
    private fun findUser(){
        showLoading(true)
        val client = ApiConfig.getApiService().getListUsers("1")
        client.enqueue(object : Callback<List<GithubResponse>> {
            override fun onResponse(
                call: Call<List<GithubResponse>>,
                response: Response<List<GithubResponse>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
//                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<GithubResponse>>, t: Throwable) {
                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    private fun setReviewData(consumerReviews: List<GithubResponse>) {
        val adapter = GithubAdapter(consumerReviews)
        binding.rvUser.adapter = adapter
        println("data: ${consumerReviews.size}")
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
        } else {
//            binding.progressBar.visibility = View.GONE
        }
    }
}