package com.example.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.data.remote.response.Follow

class FollowAdapter(var response: List<Follow>):
    RecyclerView.Adapter<FollowAdapter.GithubViewHolder>() {


    class GithubViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(githubResponse: Follow) {
            with(binding) {
                binding.tvName.text = githubResponse.login
                Glide.with(binding.root.context)
                    .load(githubResponse.avatarUrl)
                    .into(imgProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubViewHolder(itemUserBinding)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        val user = response[position]
        holder.bind(user)
    }

}
