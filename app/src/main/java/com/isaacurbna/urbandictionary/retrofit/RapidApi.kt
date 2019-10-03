package com.isaacurbna.urbandictionary.retrofit

import android.util.Log
import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.model.interfaces.ConnectionManager
import com.isaacurbna.urbandictionary.model.interfaces.OrderBy
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RapidApi(
    val onlineApi: OnlineApi,
    val offlineDao: TermsDao,
    val connectivityManager: ConnectionManager
) {

    companion object {
        private const val TAG = "RapidApi"
    }

    fun findTerm(term: String?): Single<List<Term>> {
        Log.i(TAG, "findTerm($term)")
        return findTerm(term, null)
    }

    fun findTerm(term: String?, @OrderBy.Type order: Int?): Single<List<Term>> {
        Log.i(TAG, "findTerm($term, $order)")

        if (term.isNullOrEmpty()) {
            Log.e(TAG, "findTerm: term is null, returning Single.error")
            return throwError("Term is null")
        }

        val isOnline = connectivityManager.isOnline()
        Log.i(TAG, "findTerm: isOnline: $isOnline")

        if (isOnline) {
            Log.i(TAG, "findTerm: fetching data from the web")
            // update local results
            return onlineApi.getDefinitions(term)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { response -> response.list }
                .doOnSuccess { list -> storeResult(list) }
                .map { list ->
                    when (order) {
                        OrderBy.THUMBS_UP_DESC -> list.sortedByDescending { term -> term.thumbsUp }
                        OrderBy.THUMBS_DOWN_DESC -> list.sortedByDescending { term -> term.thumbsDown }
                        else -> list
                    }
                }

        } else {
            Log.i(TAG, "findTerm: fetching data from local database")
            return offlineDao.getTerm(term)
                .doOnSuccess { list -> storeResult(list) }
                .map { list ->
                    when (order) {
                        OrderBy.THUMBS_UP_DESC -> list.sortedByDescending { term -> term.thumbsUp }
                        OrderBy.THUMBS_DOWN_DESC -> list.sortedByDescending { term -> term.thumbsDown }
                        else -> list
                    }
                }
        }
    }

    fun storeResult(termList: List<Term>) {
        Log.i(TAG, "storeResult($termList)")
        termList.forEach { offlineDao.insert(it) }
    }

    fun throwError(message: String): Single<List<Term>> {
        Log.i(TAG, "throwError($message)")
        return Single.error { Throwable("term is null") }
    }

    fun throwError(throwable: Throwable?): Single<List<Term>> {
        Log.i(TAG, "throwError($throwable)")
        return Single.error<List<Term>>(throwable)
    }
}