package me.zwsmith.datingapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.zwsmith.datingapp.data.MatchesService
import me.zwsmith.datingapp.domain.Repository
import me.zwsmith.datingapp.domain.RepositoryImpl
import me.zwsmith.datingapp.ui.matches.MatchesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC } }
    single { client(get()) }
    single { moshi() }
    single { retrofit(get(), get()) }
    single { matchesService(get()) }
    single<Repository> { RepositoryImpl(get()) }

    viewModel { MatchesViewModel(get()) }
}

private fun client(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun matchesService(retrofit: Retrofit): MatchesService {
    return retrofit.create(MatchesService::class.java)
}

fun moshi(): Moshi {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}

private const val BASE_URL = "https://www.okcupid.com/"
fun retrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .baseUrl(BASE_URL)
        .build()
}