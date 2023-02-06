package com.satdev.prueba_ceiba.featureDetail.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.satdev.prueba_ceiba.R
import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.databinding.FragmentDetailBinding
import com.satdev.prueba_ceiba.featureDetail.presentation.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private val viewModel: DetailViewModel by viewModels()
    private var _binding : FragmentDetailBinding? = null
    private val binding get() =  _binding!!

    private var _adapter : PostAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCardView()
        initRecyclerView()
        viewModel.postLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    binding.progressPost.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    binding.progressPost.visibility = View.GONE
                    adapter.setPostList(it.data ?: listOf())
                }
                is Resource.Error->{
                    binding.progressPost.visibility = View.GONE
                }
            }
        })
        viewModel.getUserPost(requireArguments().getInt("id"))
    }

    private fun initCardView() {
        if (arguments != null){
            binding.userName.setText(requireArguments().getString("name"))
            binding.userPhone.setText(requireArguments().getString("phone"))
            binding.userEmail.setText(requireArguments().getString("email"))
        }
    }

    private fun initRecyclerView() {
        _adapter = PostAdapter()
        binding.userPostList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = _adapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }
}