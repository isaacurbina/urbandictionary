package com.isaacurbna.urbandictionary.retrofit

import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.model.interfaces.ConnectionManager
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
import io.reactivex.Single

class RapidApi(
    val onlineApi: OnlineApi,
    val offlineDao: TermsDao,
    val connectivityManager: ConnectionManager
) {

    fun findTerm(term: String?): Single<List<Term>> {

        if (term.isNullOrEmpty()) {
            return Single.error { Throwable("term is null") }

        }

        if (connectivityManager.isOnline()) {
            // update local results
            onlineApi.getDefinitions(term)
            // TODO(properly store the result in room)
        }
        return offlineDao.getTerm(term)

    }
}