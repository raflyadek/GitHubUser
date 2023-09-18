package ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding
import data.response.Follow
import data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFrag : Fragment() {

    private val binding by lazy { FragmentFollowBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(KEY_USERNAME) ?: ""
        val position = arguments?.getInt(KEY_POSITION)

        when (position) {
            0 -> getFollowerList(username)
            1 -> getFollowingList(username)

        }
    }

    private fun getFollowerList(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowersList(username)
        client.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: Response<List<Follow>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showFollow(responseBody)
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

    }

    private fun getFollowingList(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowingList(username)
        client.enqueue(object : Callback<List<Follow>> {
            override fun onResponse(
                call: Call<List<Follow>>,
                response: Response<List<Follow>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showFollow(responseBody)
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Follow>>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
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