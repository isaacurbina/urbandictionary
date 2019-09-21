package com.isaacurbna.urbandictionary.retrofit

import com.isaacurbna.urbandictionary.model.ConnectivityManager
import com.isaacurbna.urbandictionary.retrofit.interfaces.RapidApiInterface
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
    lateinit var rapidApiInterface: RapidApiInterface

    @Mock
    lateinit var termsDao: TermsDao

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    var rapidApi: RapidApi? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        rapidApi = RapidApi(rapidApiInterface, termsDao, connectivityManager)
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
    fun findTerm_whenOnlineAndTermNotNull_returnsSingleFromRetrofit() {
        Mockito.`when`(connectivityManager.isOnline())
            .thenReturn(true)

        rapidApi!!.findTerm("term")

        Mockito.verify(rapidApiInterface, Mockito.times(1))
            .getDefinitions(Mockito.anyString())
    }

    @Test
    fun findTerm_whenOfflineAndTermNotNull_returnsSingleFromRoom() {
        Mockito.`when`(connectivityManager.isOnline())
            .thenReturn(false)

        rapidApi!!.findTerm("term")

        Mockito.verify(termsDao, Mockito.times(1))
            .getLocalTerms(Mockito.anyString())
    }

}