package com.isaacurbna.urbandictionary.room

import androidx.room.Dao
import com.isaacurbna.urbandictionary.retrofit.model.RapidApiResponse
import io.reactivex.Single

@Dao
interface TermsDao {

    // TODO(implement @Query and room setup)
    fun getLocalTerms(term: String): Single<RapidApiResponse>
}