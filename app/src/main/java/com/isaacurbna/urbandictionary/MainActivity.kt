package com.isaacurbna.urbandictionary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isaacurbna.urbandictionary.adapter.TermAdapter
import com.isaacurbna.urbandictionary.model.data.Term
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
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
    // endregion

    // region recycler view impl
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // dependency injection
        UrbanDictionaryApp
            .applicationComponent
            .inject(this)

        // recycler view setup
        viewManager = LinearLayoutManager(this)
        // TODO(get list from retrofit/room)
        val list: List<Term> = ArrayList()
        viewAdapter = TermAdapter(list)

        recyclerView = findViewById<RecyclerView>(R.id.termsRecyclerView)
            .apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
    }

    override fun onResume() {
        super.onResume()
    }
}
