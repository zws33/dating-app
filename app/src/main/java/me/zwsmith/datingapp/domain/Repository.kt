package me.zwsmith.datingapp.domain

import me.zwsmith.datingapp.data.MatchesResponse
import me.zwsmith.datingapp.data.MatchesService
import me.zwsmith.datingapp.di.matchesService

interface Repository {
    suspend fun getMatches(): MatchesResponse
}

class RepositoryImpl(private val matchesService: MatchesService) : Repository {

    override suspend fun getMatches(): MatchesResponse {
        return matchesService.getMatches()
    }
}