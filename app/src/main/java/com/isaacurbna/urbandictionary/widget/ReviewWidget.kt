package com.isaacurbna.urbandictionary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.isaacurbna.urbandictionary.R
import kotlinx.android.synthetic.main.widget_review.view.*
import java.security.SecureRandom

class ReviewWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.widget_review, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.ReviewWidget, 0, 0
            )

            val type = typedArray
                .getResourceId(R.styleable.ReviewWidget_type, 0)

            when (type) {
                0 -> thumbImage.scaleX = -1.0f
                1 -> thumbImage.scaleX = 1.0f
            }

            typedArray.recycle()
        }

        if (isInEditMode()) {
            val randomQty = SecureRandom().nextInt(999)
            val randomType = SecureRandom().nextBoolean()
            if (!randomType) {
                thumbImage.scaleX = -1.0f
            }
            thumbQty.text = randomQty.toString()
        }
    }

    fun setQty(qty: Int?) {
        if (qty == null) {
            visibility = GONE

        } else {
            visibility = View.VISIBLE
            thumbQty.text = qty.toString()
        }
    }
}