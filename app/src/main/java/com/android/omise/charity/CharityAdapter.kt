package com.android.omise.charity

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.omise.R
import com.android.omise.data.model.Charity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.charity_view.view.*

class CharityAdapter(
    private val charities: List<Charity>
) : RecyclerView.Adapter<CharityAdapter.ViewHolder>() {

    internal var selectCharity: (Charity) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.charity_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val charity = charities[position]
        val requestOptions = RequestOptions()
            .error(R.drawable.ic_default_image)
            .placeholder(R.drawable.ic_default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Log.d("URL", charity.url)
        Glide.with(holder.itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(charity.url)
            .into(holder.imageView)

        holder.name.text = charity.name
        holder.view.setOnClickListener {
            selectCharity(charity)
        }

    }

    override fun getItemCount(): Int = charities.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.imageView
        val name: TextView = view.nameTextView
    }
}
