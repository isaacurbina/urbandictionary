package com.isaacurbna.urbandictionary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.isaacurbna.urbandictionary.R
import com.isaacurbna.urbandictionary.model.data.Term
import kotlinx.android.synthetic.main.viewholder_term.view.*
import kotlinx.android.synthetic.main.widget_term.view.*

class TermWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.widget_term, this, true)

        if (isInEditMode()) {
            val term = Term(
                "1",
                context.getString(R.string.description),
                context.getString(R.string.author),
                context.getString(R.string.term),
                null,
                null,
                null,
                null,
                context.getString(R.string.thumbsUp).toInt(),
                context.getString(R.string.thumbsDown).toInt()
            )
            termWidget.setTerm(term)
        }
    }

    fun setTerm(term: Term) {
        termTextView.text = term.word
        descriptionTextView.text = term.definition
        authorAndDateTextView.text =
            context.getString(
                R.string.composedAuthorAndDate,
                term.author,
                term.writtenOn
            )
        thumbsUpWidget.setQty(term.thumbsUp)
        thumbsDownWidget.setQty(term.thumbsDown)

    }

}