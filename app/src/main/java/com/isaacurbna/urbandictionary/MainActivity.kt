package com.isaacurbna.urbandictionary

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isaacurbna.urbandictionary.adapter.TermAdapter
import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.retrofit.RapidApi
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
import com.isaacurbna.urbandictionary.util.ActivityUtil.Companion.hideKeyboard
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
        rapidApi.findTerm(term)
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
    }

    fun showError(error: Throwable?) {
        Log.i(TAG, "showError($error)")
        // TODO(display error message on UI)
        Log.e(TAG, "error getting data", error)
    }
    // endregion


}
