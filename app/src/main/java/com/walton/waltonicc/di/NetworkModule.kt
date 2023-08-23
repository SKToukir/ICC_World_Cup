package com.walton.waltonicc.di

import com.walton.waltonicc.api.ICCWorldCupAPI
import com.walton.waltonicc.utils.Constants.BASE_URL
import com.walton.waltonicc.utils.RestrictedSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
         return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesBannerDetailsApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): ICCWorldCupAPI {
        return retrofitBuilder.client(okHttpClient).build().create(ICCWorldCupAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).socketFactory(
                RestrictedSocketFactory(
                    DEFAULT_BUFFER_SIZE
                )
            ).build()
    }
}