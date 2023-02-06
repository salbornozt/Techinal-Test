package com.satdev.prueba_ceiba.featureDetail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satdev.prueba_ceiba.databinding.PostListItemBinding
import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var postList = arrayListOf<UserPost>()

    fun setPostList(list: List<UserPost>){
        postList.clear()
        postList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList.get(position))
    }

    override fun getItemCount(): Int = postList.size

    inner class PostViewHolder(private val binding: PostListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: UserPost){
            binding.postTitle.setText(item.title)
            binding.postBody.setText(item.body)

        }

    }
}