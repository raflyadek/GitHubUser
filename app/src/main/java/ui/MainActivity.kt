package ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import data.response.GithubDetail
import data.response.GithubResponse
import data.response.User
import data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
//                .setOnEditorActionListener { textView, actionId, event ->
//                    searchBar.text = searchView.
//                }
        }

        findUser()
    }
    
    private fun findUser(){
        showLoading(true)
        val client = ApiConfig.getApiService().getListUsers("rafly")
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showUser(responseBody.items)
                    }
                } else {
                   Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    private fun showUser(listUser: List<User>?) {
        if (listUser != null) {
            val layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
            val adapter = GithubAdapter(listUser)
            binding.rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback{
                override fun onItemClicked(data: User) {
                    val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    intentToDetail.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                    startActivity(intentToDetail)
                }
            })
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
