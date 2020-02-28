package me.zwsmith.datingapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.zwsmith.datingapp.data.MatchDto
import me.zwsmith.datingapp.data.MatchesService

interface Repository {
    suspend fun getMatches()
    val matches: LiveData<List<Match>>
    val selectedMatches: LiveData<List<Pair<Match, Boolean>>>
}

class RepositoryImpl(private val matchesService: MatchesService) : Repository {

    override val matches: LiveData<List<Match>> get() = _matches
    private val _matches = MutableLiveData<List<Match>>()

    private val _selected = MutableLiveData<List<String>>().apply { emptyList<String>() }

    override val selectedMatches: LiveData<List<Pair<Match, Boolean>>> =
        zip(_matches, _selected) { matches, selections ->
            matches.map { it to !selections.contains(it.userid) }
        }

    override suspend fun getMatches() {
        val matchesResponse = withContext(Dispatchers.IO) {
            matchesService.getMatches()
        }
        _selected.postValue(emptyList())
        _matches.postValue(matchesResponse.data.map { it.toMatch() })
    }
}

fun <T, R, U> zip(
    sourceOne: LiveData<T>,
    sourceTwo: LiveData<R>,
    transformation: (T, R) -> U
): LiveData<U> {
    return MediatorLiveData<U>().apply {
        var lastA: T? = null
        var lastB: R? = null

        fun update() {
            val localLastT = lastA
            val localLastR = lastB
            if (localLastT != null && localLastR != null) {
                this.value = transformation(localLastT, localLastR)
            }
        }

        addSource(sourceOne) {
            lastA = it
            update()
        }
        addSource(sourceTwo) {
            lastB = it
            update()
        }
    }
}

fun MatchDto.toMatch(): Match {
    return Match(
        age = this.age,
        cityName = this.cityName,
        countryCode = this.countryCode,
        countryName = this.countryName,
        enemy = this.enemy,
        friend = this.friend,
        gender = this.gender,
        genderTags = this.genderTags,
        is_online = this.is_online,
        lastContactTime = this.lastContactTime,
        lastLogin = this.lastLogin,
        liked = this.liked,
        location = this.location,
        match = this.match,
        orientation = this.orientation,
        orientationTags = this.orientationTags,
        photo = this.photo,
        relative = this.relative,
        stateCode = this.stateCode,
        stateName = this.stateName,
        stoplightColor = this.stoplightColor,
        userid = this.userid,
        username = this.username
    )
}

