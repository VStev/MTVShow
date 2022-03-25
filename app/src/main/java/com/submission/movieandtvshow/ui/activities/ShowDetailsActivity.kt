package com.submission.movieandtvshow.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.databinding.ActivityShowDetailsBinding
import com.submission.movieandtvshow.dataobjects.Movie
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.viewmodelproviders.ShowDetailsViewModel
import com.submission.movieandtvshow.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class ShowDetailsActivity : AppCompatActivity() {

    private lateinit var viewBind: ActivityShowDetailsBinding
    private val detailViewModel: ShowDetailsViewModel by viewModel()
    private lateinit var showID: String
    private var menu: Menu? = null
    private var state by Delegates.notNull<Boolean>()
    private var argument: Int = 0

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_ARG = "args"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argument = intent.getIntExtra(EXTRA_ARG, 1)
        showID = intent.getStringExtra(EXTRA_ID).toString()
        viewBind = ActivityShowDetailsBinding.inflate(layoutInflater)
        detailViewModel.setShowID(showID)
        setContentView(viewBind.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.star_menu, menu)
        this.menu = menu
        val contentDesc: TextView = findViewById(R.id.description)
        val seasonText: TextView = findViewById(R.id.season_and_episode_count)
        val ongoingText: TextView = findViewById(R.id.director_or_ongoing)
        when (argument) {
            1 -> {
                detailViewModel.getShow().observe(this) { TVShow ->
                    if (TVShow != null) {
                        when (TVShow.status) {
                            //Status.LOADING -> do something
                            Status.SUCCESS -> {
                                val imageUrl = "https://image.tmdb.org/t/p/w500"
                                val url = imageUrl + TVShow.data?.poster
                                supportActionBar?.title = TVShow.data?.title
                                Glide.with(this)
                                    .load(url)
                                    .into(viewBind.posterImage)
                                viewBind.titleText.text = TVShow.data?.title
                                viewBind.releaseYear.text = TVShow.data?.releaseYear
                                val ongoing =
                                    if (TVShow.data?.ongoing == true) getString(R.string.ongoing_text) else getString(
                                        R.string.season_completed_text
                                    )
                                viewBind.directorOrOngoing.text = ongoing
                                val season =
                                    if (TVShow.data?.seasons as Int > 1) "${TVShow.data.seasons} Seasons" else "${TVShow.data.seasons} Season"
                                val episodes =
                                    if (TVShow.data.episodes as Int > 1) "${TVShow.data.episodes} Episodes" else "${TVShow.data.episodes} Episode"
                                val displayText = "$season / $episodes"
                                seasonText.text = displayText
                                contentDesc.text = TVShow.data.details
                                state = TVShow.data.fav
                                setBookmarkState(state)
                            }
                            Status.ERROR -> {
                                viewBind.dataFound.visibility = View.GONE
                                viewBind.notFound.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
            2 -> {
                detailViewModel.getMovie().observe(this, { Movie ->
                    if (Movie != null){
                        when(Movie.status){
                            //Status.LOADING -> do something
                            Status.SUCCESS -> {
                                val imageUrl = "https://image.tmdb.org/t/p/w500"
                                val url = imageUrl + Movie.data?.poster
                                supportActionBar?.title = Movie.data?.title
                                seasonText.visibility = View.GONE
                                ongoingText.visibility = View.GONE
                                Glide.with(this)
                                    .load(url)
                                    .into(viewBind.posterImage)
                                viewBind.titleText.text = Movie.data?.title
                                viewBind.releaseYear.text = Movie.data?.releaseYear
                                contentDesc.text = Movie.data?.details
                                if (Movie.data?.fav != null){
                                    state = Movie.data.fav
                                }
                                setBookmarkState(state)
                            }
                            Status.ERROR -> {
                                viewBind.dataFound.visibility = View.GONE
                                viewBind.notFound.visibility = View.VISIBLE
                            }
                        }
                    }
                })
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            state = !state
            detailViewModel.setFav(argument, state)
            setBookmarkState(state)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_border_24)
        }
    }

}