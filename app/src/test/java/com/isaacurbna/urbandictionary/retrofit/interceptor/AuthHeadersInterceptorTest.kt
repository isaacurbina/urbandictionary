package com.isaacurbna.urbandictionary.retrofit.interceptor

import com.isaacurbna.urbandictionary.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/*
 class okhttp3.Request is final, therefore, Mockito needs to be
 configured to mock final classes with plugin mock-maker-inline enabled in file:
 Name: org.mockito.plugins.MockMaker
 Location: src/test/resources/mockito-extensions
 */
class AuthHeadersInterceptorTest {

    @Mock
    lateinit var mockChain: Interceptor.Chain
    @Mock
    lateinit var mockRequest: Request
    @Mock
    lateinit var mockBuilder: Request.Builder
    @Mock
    lateinit var mockBody: RequestBody
    @Mock
    lateinit var mockResponse: Response

    lateinit var interceptor: AuthHeadersInterceptor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interceptor = AuthHeadersInterceptor()

        // region Mockito setup
        // Chain
        Mockito.`when`(mockChain.request())
            .thenReturn(mockRequest)
        Mockito.`when`(mockChain.proceed(Mockito.any(Request::class.java)))
            .thenReturn(mockResponse)

        // Request
        Mockito.`when`(mockRequest.newBuilder())
            .thenReturn(mockBuilder)
        Mockito.`when`(mockRequest.method())
            .thenReturn("GET")
        Mockito.`when`(mockRequest.body())
            .thenReturn(mockBody)

        // Request.Builder
        Mockito.`when`(mockBuilder.header(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(mockBuilder)
        Mockito.`when`(mockBuilder.build())
            .thenReturn(mockRequest)
        Mockito.`when`(
            mockBuilder.method(
                Mockito.anyString(),
                Mockito.any(RequestBody::class.java)
            )
        ).thenReturn(mockBuilder)
        Mockito.`when`(mockBuilder.build())
            .thenReturn(mockRequest)
        // endregion
    }

    @Test
    fun intercept_receivesRequest_appendsTwoHeaders() {
        interceptor.intercept(mockChain)
        Mockito.verify(mockBuilder, Mockito.times(1))
            .header(AuthHeadersInterceptor.HOST_HEADER, BuildConfig.API_HOST)
        Mockito.verify(mockBuilder, Mockito.times(1))
            .header(AuthHeadersInterceptor.KEY_HEADER, BuildConfig.API_KEY)
    }
}