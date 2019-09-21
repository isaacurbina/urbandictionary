package com.isaacurbna.urbandictionary.retrofit.interceptor

import com.isaacurbna.urbandictionary.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AuthHeadersInterceptorTest {

    @Mock
    lateinit var mockChain: Interceptor.Chain
    @Mock
    lateinit var mockRequest: Request
    @Mock
    lateinit var mockBuilder: Request.Builder
    @Mock
    lateinit var mockBody: RequestBody

    var interceptor: AuthHeadersInterceptor? = null

    @Before
    fun setUp() {
        // TODO(Request is a final class so it cannot be mocked, find a workaround)
        MockitoAnnotations.initMocks(this)
        interceptor = AuthHeadersInterceptor()

        // Setup for mocks
        Mockito.`when`(mockChain.request())
            .thenReturn(mockRequest)
        Mockito.`when`(mockRequest.newBuilder())
            .thenReturn(mockBuilder)
        Mockito.`when`(mockBuilder.header(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(mockBuilder)
        Mockito.`when`(mockBuilder.build()).thenReturn(mockRequest)
        Mockito.`when`(mockRequest.method()).thenReturn("GET")
        Mockito.`when`(mockRequest.body()).thenReturn(mockBody)
    }

    @After
    fun tearDown() {
        interceptor = null
    }

    @Test
    fun intercept_receivesRequest_appendsTwoHeaders() {
        interceptor!!.intercept(mockChain)
        Mockito.verify(mockBuilder, Mockito.times(1))
            .header(AuthHeadersInterceptor.HOST_HEADER, BuildConfig.API_HOST)
        Mockito.verify(mockBuilder, Mockito.times(1))
            .header(AuthHeadersInterceptor.KEY_HEADER, BuildConfig.API_KEY)
    }
}