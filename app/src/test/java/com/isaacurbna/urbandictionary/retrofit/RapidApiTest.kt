package com.isaacurbna.urbandictionary.retrofit

import com.isaacurbna.urbandictionary.model.interfaces.ConnectionManager
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
import io.reactivex.internal.operators.single.SingleError
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RapidApiTest {

    @Mock
    lateinit var onlineApi: OnlineApi

    @Mock
    lateinit var termsDao: TermsDao

    @Mock
    lateinit var connectivityManager: ConnectionManager

    var rapidApi: RapidApi? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        rapidApi = RapidApi(onlineApi, termsDao, connectivityManager)
    }

    @After
    fun tearDown() {
        rapidApi = null
    }

    @Test
    fun findTerm_whenTermNull_returnsSingleOnErrorFromRetrofit() {
        val single = rapidApi!!.findTerm(null)
        assertTrue(single is SingleError)
    }

    @Test
    fun findTerm_whenOnlineAndTermNotNull_refreshesCachedDataFromRetrofit() {
        Mockito.`when`(connectivityManager.isOnline())
            .thenReturn(true)

        rapidApi!!.findTerm("term")

        Mockito.verify(onlineApi, Mockito.times(1))
            .getDefinitions(Mockito.anyString())
        Mockito.verify(termsDao, Mockito.times(1))
            .getTerm(Mockito.anyString())
    }

    @Test
    fun findTerm_whenOfflineAndTermNotNull_returnsSingleFromRoom() {
        Mockito.`when`(connectivityManager.isOnline())
            .thenReturn(false)

        rapidApi!!.findTerm("term")

        Mockito.verify(onlineApi, Mockito.never())
            .getDefinitions(Mockito.anyString())
        Mockito.verify(termsDao, Mockito.times(1))
            .getTerm(Mockito.anyString())
    }

}