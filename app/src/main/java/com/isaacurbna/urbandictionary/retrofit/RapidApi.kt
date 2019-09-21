package com.isaacurbna.urbandictionary.retrofit

import com.isaacurbna.urbandictionary.model.ConnectivityManager
import com.isaacurbna.urbandictionary.retrofit.interfaces.RapidApiInterface
import com.isaacurbna.urbandictionary.retrofit.model.RapidApiResponse
import com.isaacurbna.urbandictionary.room.TermsDao
import io.reactivex.Single

class RapidApi(
    val rapidApiInterface: RapidApiInterface,
    val termsDao: TermsDao,
    val connectivityManager: ConnectivityManager
) {

    fun findTerm(term: String?): Single<RapidApiResponse> {

        if (term.isNullOrEmpty()) {
            return Single.error { Throwable("term is null") }

        } else if (connectivityManager.isOnline()) {
            // TODO(store the result in room as well)
            return rapidApiInterface.getDefinitions(term)

        } else {
            return termsDao.getLocalTerms(term)
        }
    }
}