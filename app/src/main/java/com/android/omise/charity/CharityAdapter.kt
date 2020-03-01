package com.android.omise.charity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.omise.R
import com.android.omise.data.model.Charity
import com.android.omise.util.loadImage
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

        holder.imageView.loadImage(holder.itemView.context, charity.url)

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
