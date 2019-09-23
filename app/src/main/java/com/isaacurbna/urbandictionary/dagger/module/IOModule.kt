package com.isaacurbna.urbandictionary.dagger.module

import android.content.Context
import com.isaacurbna.urbandictionary.BuildConfig
import com.isaacurbna.urbandictionary.entities.ConnectionChecker
import com.isaacurbna.urbandictionary.model.interfaces.ConnectionManager
import com.isaacurbna.urbandictionary.retrofit.interceptor.AuthHeadersInterceptor
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.OfflineDatabase
import com.isaacurbna.urbandictionary.room.TermsDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class IOModule {

    @Singleton
    @Provides
    fun providesConnectionManager(context: Context): ConnectionManager {
        return ConnectionChecker(context)
    }

    @Singleton
    @Provides
    fun providesOfflineDatabase(context: Context): OfflineDatabase {
        return OfflineDatabase.getAppDataBase(context)!!
    }

    @Singleton
    @Provides
    fun providesTermsDao(offlineDatabase: OfflineDatabase): TermsDao {
        return offlineDatabase.termsDao()
    }

    @Singleton
    @Provides
    fun providesAuthHeadersInterceptor(): AuthHeadersInterceptor {
        return AuthHeadersInterceptor()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authHeadersInterceptor: AuthHeadersInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(authHeadersInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
        return builder.build()
    }

    @Singleton
    @Provides
    fun providesOnlineApi(okHttpClient: OkHttpClient): OnlineApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(OnlineApi::class.java)
    }
}