package ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ItemUserBinding
import data.response.User

class GithubAdapter(var response: List<User>):
    RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    class GithubViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(githubResponse: User){
            binding.tvName.text = githubResponse.login
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
