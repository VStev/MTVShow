package com.submission.movieandtvshow.ui.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.movieandtvshow.R
import com.submission.movieandtvshow.databinding.CardviewLayoutBinding
import com.submission.movieandtvshow.dataobjects.TVShow
import com.submission.movieandtvshow.ui.activities.ShowDetailsActivity

class TVAdapter : PagedListAdapter<TVShow, TVAdapter.CardViewHolder>(DIFF_CALLBACK) {

    private val mData = ArrayList<TVShow>()
    private val imageUrl = "https://image.tmdb.org/t/p/w500"

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVShow>() {
            override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
                return oldItem.showID == newItem.showID
            }

            override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setData(items: List<TVShow>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(items: View) : RecyclerView.ViewHolder(items) {
        private val binding = CardviewLayoutBinding.bind(itemView)
        fun bind(showData: TVShow) {
            val url = imageUrl + showData.poster
            Glide.with(binding.root)
                .load(url)
                .into(binding.imagePosterThumbnail)
            binding.textTitle.text = showData.title
            binding.year.text = showData.releaseYear
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ShowDetailsActivity::class.java)
                intent.putExtra(ShowDetailsActivity.EXTRA_ID, showData.showID)
                intent.putExtra(ShowDetailsActivity.EXTRA_ARG, 1)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}