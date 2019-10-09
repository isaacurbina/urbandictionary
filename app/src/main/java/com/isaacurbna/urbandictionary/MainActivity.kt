package com.isaacurbna.urbandictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.isaacurbna.urbandictionary.adapter.TermAdapter
import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.model.interfaces.OrderBy
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

    // region attributes
    var orderByValue: String? = null
    // endregion

    // region interfaces impl
    val spinnerListener = object : OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.i(TAG, "orderByValue= $orderByValue")
            orderByValue = null
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            orderByValue = parent?.getItemAtPosition(position).toString()
            Log.i(TAG, "orderByValue= $orderByValue")

            val searchTerm = getSearchTerm()
            Log.i(TAG, "searchTerm= $searchTerm")

            if (!searchTerm.isNullOrEmpty()) {
                fetchData(searchTerm, getOrderBy())
            }
        }

    }
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

        // set up spinner data
        setupSpinner()
    }

    override fun onResume() {
        super.onResume()

        searchBarEditText.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    fetchData(getSearchTerm(), getOrderBy())
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
    fun setupSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.order_by_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            orderBySpinner.adapter = adapter
            orderBySpinner.onItemSelectedListener = spinnerListener
        }
    }

    @SuppressLint("CheckResult")
    fun fetchData(term: String?, orderBy: Int?) {
        Log.i(TAG, "fetchData($term)")
        rapidApi.findTerm(term, orderBy)
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

    fun getSearchTerm(): String? =
        searchBarEditText.text.toString()

    fun getOrderBy(): Int? {
        var value: Int? = null
        Log.i(TAG, "orderByValue= $orderByValue")

        when (orderByValue) {
            getString(R.string.mostThumbsUp) -> value = OrderBy.THUMBS_UP_DESC
            getString(R.string.mostThumbsDown) -> value = OrderBy.THUMBS_DOWN_DESC
            else -> value = null
        }
        return value
    }
// endregion


}
