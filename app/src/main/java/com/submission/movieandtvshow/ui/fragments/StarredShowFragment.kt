package com.submission.movieandtvshow.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.databinding.FragmentStarredShowBinding
import com.submission.movieandtvshow.viewmodelproviders.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarredShowFragment : Fragment() {
    private var binding : FragmentStarredShowBinding? = null
    private val viewBind get() = binding as FragmentStarredShowBinding
    private lateinit var recyclerView: RecyclerView
    private val movieViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarredShowBinding.inflate(inflater, container, false)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        showLayout()
    }

    private fun showLayout() {
        val dataAdapter = TVAdapter()
        movieViewModel.getFavouriteShows().observe(viewLifecycleOwner, { TVShow ->
            if (TVShow.isNotEmpty()){
                dataAdapter.setData(TVShow)
                dataAdapter.submitList(TVShow)
            }else{
                viewBind.recyclerView.visibility = View.GONE
                viewBind.notFound.visibility = View.VISIBLE
            }
        })
        with(recyclerView){
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }
    }
}