package com.isaacurbna.urbandictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.isaacurbna.urbandictionary.R
import com.isaacurbna.urbandictionary.model.data.Term

class TermAdapter(private val termList: List<Term>) :
    RecyclerView.Adapter<TermViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        val frameLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_term, parent, false) as FrameLayout
        return TermViewHolder(frameLayout)
    }

    override fun getItemCount(): Int {
        return termList.size
    }

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        val term = termList[position]
        holder.bind(term)
    }


}