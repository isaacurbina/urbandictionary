package com.isaacurbna.urbandictionary.room

import androidx.room.*
import com.isaacurbna.urbandictionary.model.data.Term
import io.reactivex.Single

@Dao
interface TermsDao {

    // region create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(term: Term)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg term: Term)
    // endregion

    // region read
    @Query("SELECT * FROM term")
    fun getAllTerms(): Single<List<Term>>

    @Query("SELECT * FROM term WHERE word=:term")
    fun getTerm(term: String): Single<List<Term>>

    @Query("SELECT * FROM term WHERE word=:term ORDER BY :field DESC")
    fun getTermOrderedBy(term: String, field: String): Single<List<Term>>
    // endregion

    // region update
    @Update
    fun update(term: Term)
    // endregion

    // region delete
    @Delete
    fun delete(term: Term)
    // endregion
}