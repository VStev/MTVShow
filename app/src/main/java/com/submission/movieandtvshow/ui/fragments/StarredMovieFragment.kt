package com.submission.movieandtvshow.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.databinding.FragmentStarredMovieBinding
import com.submission.movieandtvshow.viewmodelproviders.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarredMovieFragment : Fragment() {
    private var binding : FragmentStarredMovieBinding? = null
    private val viewBind get() = binding as FragmentStarredMovieBinding
    private lateinit var recyclerView: RecyclerView
    private val movieViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarredMovieBinding.inflate(inflater, container, false)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        showLayout()
    }

    private fun showLayout() {
        val dataAdapter = MovieAdapter()
        movieViewModel.getFavouriteMovies().observe(viewLifecycleOwner, { Movie ->
            if (Movie.isNotEmpty()){
                dataAdapter.setData(Movie)
                dataAdapter.submitList(Movie)
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