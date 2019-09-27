package com.isaacurbna.urbandictionary

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.isaacurbna.urbandictionary.adapter.TermAdapter
import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.retrofit.RapidApi
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
import com.isaacurbna.urbandictionary.util.ActivityUtil.Companion.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    // region dependencies
    @Inject
    lateinit var termsDao: TermsDao
    @Inject
    lateinit var onlineApi: OnlineApi
    @Inject
    lateinit var rapidApi: RapidApi
    // endregion

    // region recycler view impl
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    // endregion

    // region lifecycle callbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // dependency injection
        UrbanDictionaryApp
            .applicationComponent
            .inject(this)

        // recycler view setup
        viewManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        searchBarEditText.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    fetchData(searchBarEditText.text.toString())
                    this.hideKeyboard(searchBarEditText)
                    progressBar.visibility = View.VISIBLE
                    progressBar.requestFocus()
                    searchBarEditText.isEnabled = false
                    true
                }
                else -> false
            }
        }
    }
    // endregion

    // region helper methods
    fun fetchData(term: String) {
        Log.i(TAG, "fetchData($term)")
        val single = rapidApi.findTerm(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> showResults(result) },
                { error -> showError(error) }
            )
    }

    fun showResults(termList: List<Term>) {
        Log.i(TAG, "showResults($termList)")
        viewAdapter = TermAdapter(termList)

        recyclerView = findViewById<RecyclerView>(R.id.termsRecyclerView)
            .apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        progressBar.visibility = View.GONE
        searchBarEditText.isEnabled = true
    }

    fun showError(error: Throwable?) {
        Log.i(TAG, "showError($error)")
        Log.e(TAG, "error getting data", error)
        Snackbar.make(searchBarEditText, error?.localizedMessage.toString(), Snackbar.LENGTH_LONG)
            .show()
        progressBar.visibility = View.GONE
        searchBarEditText.isEnabled = true
    }
// endregion


}
