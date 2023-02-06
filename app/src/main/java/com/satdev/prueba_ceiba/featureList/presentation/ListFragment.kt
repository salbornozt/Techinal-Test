package com.satdev.prueba_ceiba.featureList.presentation

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satdev.prueba_ceiba.R
import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.databinding.FragmentListBinding
import com.satdev.prueba_ceiba.featureList.data.model.User
import com.satdev.prueba_ceiba.featureList.presentation.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), UserListAdapter.UserClickListener, UserListAdapter.UserFilterListener {


    private val viewModel: ListViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var _adapter: UserListAdapter? = null
    private val adapter get() = _adapter!!

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initProgressDialog()

        viewModel.userListLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    showProgressDialog(true)
                }
                is Resource.Success -> {
                    showProgressDialog(false)
                    adapter.setUserList(it.data ?: listOf())
                    initSearchFilter()

                }
                is Resource.Error -> {
                    showProgressDialog(false)

                }
            }
        })


    }

    private fun initSearchFilter() {

        binding.userListSearch.addTextChangedListener {
            adapter.filter.filter(it.toString())

        }
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle(R.string.progress_dialog_title)
        progressDialog.setMessage(getString(R.string.progress_dialog_message))
    }

    private fun initRecyclerView() {
        _adapter = UserListAdapter(this,this)
        binding.userList.layoutManager = LinearLayoutManager(activity)
        binding.userList.adapter = adapter
        /*binding.userList.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {
                Log.d("sat_tag", "onChildViewDetachedFromWindow: ${adapter.itemCount}")

                //binding.emptyListMessage.visibility = View.GONE
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                Log.d("sat_tag", "onChildViewDetachedFromWindow: ${adapter.itemCount}")
                if (adapter.itemCount == 0) {
                    binding.emptyListMessage.visibility = View.VISIBLE

                }else if (adapter.itemCount > 0){
                    binding.emptyListMessage.visibility = View.GONE
                }
            }
        })*/

    }

    private fun showProgressDialog(show: Boolean) {
        if (show) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    override fun onFilterResult(count: Int) {
        if (adapter.itemCount == 0) {
            binding.emptyListMessage.visibility = View.VISIBLE

        }else if (adapter.itemCount > 0){
            binding.emptyListMessage.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }

    override fun onUserClick(user: User, position: Int) {
        findNavController().navigate(
            R.id.action_listFragment_to_detailFragment,
            bundleOf("id" to user.id, "name" to user.name, "phone" to user.phone,"email" to user.email)
        )
    }
}