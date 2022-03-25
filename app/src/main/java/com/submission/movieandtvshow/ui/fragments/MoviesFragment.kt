package com.submission.movieandtvshow.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.databinding.FragmentMoviesBinding
import com.submission.movieandtvshow.viewmodelproviders.MainViewModel
import com.submission.movieandtvshow.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var binding : FragmentMoviesBinding? = null
    private val viewBind get() = binding as FragmentMoviesBinding
    private lateinit var recyclerView: RecyclerView
    private val movieViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        showLayout()
    }

    private fun showLayout() {
        val dataAdapter = MovieAdapter()
        movieViewModel.getMovies().observe(viewLifecycleOwner, { Movie ->
            if (Movie != null){
                when(Movie.status){
                    //Status.LOADING -> do something
                    Status.SUCCESS -> {
                        dataAdapter.notifyItemRangeRemoved(0, 20)
                        dataAdapter.notifyItemRangeInserted(0, 20)
                        Movie.data?.let { dataAdapter.setData(it) }
                        dataAdapter.notifyDataSetChanged()
                        Movie.data?.let { dataAdapter.submitList(it) }
                    }
                    Status.ERROR -> {
                        viewBind.recyclerView.visibility = View.GONE
                        viewBind.notFound.visibility = View.VISIBLE
                    }
                }
                with(recyclerView){
                    layoutManager = LinearLayoutManager(context)
                    adapter = dataAdapter
                }
            }
        })
    }
}