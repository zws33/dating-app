package me.zwsmith.datingapp.data

import retrofit2.http.GET

interface MatchesService {
    @GET("matchSample.json")
    suspend fun getMatches(): MatchesResponse
}