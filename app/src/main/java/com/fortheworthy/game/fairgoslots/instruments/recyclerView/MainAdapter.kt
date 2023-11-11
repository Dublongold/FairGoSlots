package com.fortheworthy.game.fairgoslots.instruments.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fortheworthy.game.fairgoslots.R
import com.fortheworthy.game.fairgoslots.viewPart.activities.MainActivity.Companion.ARTICLE_DESTINATION

class MainAdapter(
    private val callback: (String, Int) -> Unit
): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.itemView.run {
            val title = resources.getStringArray(R.array.article_titles)[position]
            val image = R.drawable.img01 + position

            findViewById<TextView>(R.id.articleTitle).text = title
            findViewById<ImageView>(R.id.articleImage).setImageResource(image)
            findViewById<LinearLayout>(R.id.articleComponents).setOnClickListener {
                Log.i("Adapter", position.toString())
                callback(ARTICLE_DESTINATION, position)
            }
        }
    }

    class MainViewHolder(view: View): ViewHolder(view)
}