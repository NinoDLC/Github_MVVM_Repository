package fr.delcey.github_mvvm_repository.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.delcey.github_mvvm_repository.R
import fr.delcey.github_mvvm_repository.ui.main.MainViewModel.ListItem

class MainRecyclerViewAdapter(private val listener: OnGithubRepoClickedListener) :
    ListAdapter<ListItem, MainRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.main_itemview,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.repositoryUrl == newItem.repositoryUrl

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.author == newItem.author
                    && oldItem.repositoryName == newItem.repositoryName
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button = itemView.findViewById<Button>(R.id.main_item_btn)
        private val textViewName = itemView.findViewById<TextView>(R.id.main_item_tv_repo_name)
        private val textViewAuthor = itemView.findViewById<TextView>(R.id.main_item_tv_repo_author)

        fun bind(
            listItem: ListItem,
            listener: OnGithubRepoClickedListener
        ) {
            button.setOnClickListener {
                listener.onGithubRepoClicked(listItem.repositoryUrl!!)
            }

            textViewName.text = listItem.repositoryName
            textViewAuthor.text = listItem.author
        }
    }

    interface OnGithubRepoClickedListener {
        fun onGithubRepoClicked(url: String)
    }
}