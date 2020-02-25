package me.zwsmith.datingapp.di

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
    single { retrofit(get()) }
    single { matchesService(get()) }
    single<Repository> { RepositoryImpl(get()) }

    viewModel { MatchesViewModel(get()) }
}

private fun client(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun matchesService(retrofit: Retrofit): MatchesService{
    return retrofit.create(MatchesService::class.java)
}

private const val BASE_URL = "https://www.okcupid.com/"
fun retrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build()
}