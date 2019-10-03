package com.isaacurbna.urbandictionary.model.interfaces

import androidx.annotation.IntDef

class OrderBy {

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.VALUE_PARAMETER)
    @IntDef(THUMBS_UP_DESC, THUMBS_DOWN_DESC)
    annotation class Type

    companion object {
        const val THUMBS_UP_DESC = 1
        const val THUMBS_DOWN_DESC = 2
    }
}