package ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.databinding.ItemUserBinding
import data.response.GithubResponse

class GithubAdapter (var response: List<GithubResponse>):
    RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    class GithubViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(githubResponse: GithubResponse){
            binding.tvName.text = githubResponse.name
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
