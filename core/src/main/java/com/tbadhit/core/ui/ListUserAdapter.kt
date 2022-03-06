package com.tbadhit.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tbadhit.core.databinding.ItemRowUserBinding
import com.tbadhit.core.domain.model.User

class ListUserAdapter(
    private val users: ArrayList<User>,
    private val clickListener: (User) -> Unit
) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(users[position], clickListener)
    }

    override fun getItemCount(): Int = users.size

    fun setListUser(items: List<User>?) {
        users.apply {
            clear()
            items?.let { addAll(it) }
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, click: (User) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions())
                    .into(imgItemPhoto)
                tvUsername.text = user.username
                itemView.setOnClickListener {
                    click(user)
                }
            }
        }
    }
}