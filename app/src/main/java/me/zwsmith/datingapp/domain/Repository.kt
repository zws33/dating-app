package me.zwsmith.datingapp.domain

import me.zwsmith.datingapp.data.MatchesService

interface Repository

class RepositoryImpl(machesService: MatchesService) : Repository {

}