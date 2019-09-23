package com.isaacurbna.urbandictionary.adapter

import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.isaacurbna.urbandictionary.R
import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.widget.TermWidget

class TermViewHolder(private val frameLayout: FrameLayout) :
    RecyclerView.ViewHolder(frameLayout) {

    private var termWidget: TermWidget? = null

    init {
        termWidget = frameLayout.findViewById(R.id.termWidget)
    }

    fun bind(term: Term) {
        termWidget?.setTerm(term)
    }
}