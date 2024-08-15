package com.example.suitmediamobiletest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediamobiletest.R
import com.example.suitmediamobiletest.data.response.DataItem

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var userList = listOf<DataItem>()
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.user_image)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_userName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]
        Glide.with(holder.ivAvatar.context)
            .load(currentItem.avatar)
            .circleCrop()
            .into(holder.ivAvatar)

        holder.let {
            it.tvEmail.text = currentItem.email
            it.tvUsername.text = "${currentItem.firstName} ${currentItem.lastName}"
            it.itemView.setOnClickListener {
                onItemClickListener.onItemClick(currentItem)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: DataItem)
    }
}