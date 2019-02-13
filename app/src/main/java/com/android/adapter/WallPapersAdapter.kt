package com.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TextView
import com.android.modal.PhotosItem
import com.android.mywallpapers.R
import com.bumptech.glide.Glide

class WallPapersAdapter(private val context: Context, private var wallPapers: ArrayList<PhotosItem>, private val clickListener: (PhotosItem) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_CARD_EXPAND = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_wallpaper_view, parent, false)
        return WallPaperHolder(itemView)
    }

    fun setData(wallPapers: ArrayList<PhotosItem>?) {
        wallPapers?.let {
            this.wallPapers.addAll(wallPapers)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return wallPapers.size
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_CARD_EXPAND
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = wallPapers[position]
        if (holder is WallPapersAdapter.WallPaperHolder) {
            holder.name.text = item.photographer
            Glide.with(context).load(item.src?.medium).into(holder.image)
            holder.itemView.setOnClickListener {
                clickListener(item)
            }
        }
    }

    class WallPaperHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var image: ImageView = view.findViewById(R.id.wallpaper_image)
    }
}