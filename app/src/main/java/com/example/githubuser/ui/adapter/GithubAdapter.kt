package com.example.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.data.remote.response.User

class GithubAdapter(var response: List<User>):
    RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class GithubViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(githubResponse: User) {
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
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(user)}
    }

}
