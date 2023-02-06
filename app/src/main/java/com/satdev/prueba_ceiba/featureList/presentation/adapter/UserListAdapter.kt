package com.satdev.prueba_ceiba.featureList.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.satdev.prueba_ceiba.databinding.UserListItemBinding
import com.satdev.prueba_ceiba.featureList.data.model.User

class UserListAdapter (private val listener: UserClickListener, private val userFilterListener: UserFilterListener) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>(),Filterable{
    interface UserClickListener{
        fun onUserClick(user: User, position:Int)
    }
    interface UserFilterListener{
        fun onFilterResult(count:Int)
    }
    private var userList = arrayListOf<User>()
    private var userListFull = arrayListOf<User>()

    fun setUserList(list:List<User>){
        userList.clear()
        userList.addAll(list)
        userListFull = ArrayList(list)
        Log.d("sat_tag", "performFiltering: lista pp  ${userListFull.size}")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList.get(position))
    }

    override fun getItemCount(): Int = userList.size

    override fun getFilter(): Filter = itemFilter

    private val itemFilter:Filter = object : Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList: ArrayList<User> = ArrayList()
            if (p0 == null || p0.length == 0) {
                Log.d("sat_tag", "performFiltering: lista ${userListFull.size}")
                filteredList.addAll(userListFull)
            } else {
                val pattern = p0.toString().lowercase().trim { it <= ' ' }
                for (user in userListFull) {
                    try {
                        if (user.name != null && user.name!!.lowercase().trim().contains(pattern)) {
                            filteredList.add(user)

                        }
                    } catch (e: Exception) {
                        Log.d("error", "performFiltering: "+e.message)
                    }
                }
            }
            val results = FilterResults()
            Log.d("sat_tag", "performFiltering: ${filteredList.size}")

            results.values = filteredList

            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            userList.clear()
            userList.addAll(p1?.values as Collection<User>)
            userFilterListener.onFilterResult(p1.count)
            notifyDataSetChanged()
        }
    }

    inner class UserViewHolder(private val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.btnGoToPost.setOnClickListener(this)
        }
        fun bind(item: User){
            binding.userEmail.setText(item.email)
            binding.userName.setText(item.name)
            binding.userPhone.setText(item.phone)
        }

        override fun onClick(p0: View?) {
            listener.onUserClick(userList.get(adapterPosition),adapterPosition)
        }
    }
}