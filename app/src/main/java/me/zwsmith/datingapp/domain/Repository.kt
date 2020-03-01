package me.zwsmith.datingapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.zwsmith.datingapp.data.Match
import me.zwsmith.datingapp.data.MatchesService

interface Repository {
    suspend fun refreshMatches()
    fun updateLiked(userId: String)
    val availableMatches: LiveData<List<Match>>
    val likedMatchIds: LiveData<List<String>>
}

class RepositoryImpl(private val matchesService: MatchesService) : Repository {

    override val availableMatches: LiveData<List<Match>> get() = _availableMatches
    private val _availableMatches = MutableLiveData<List<Match>>().apply { value = emptyList() }

    override val likedMatchIds: LiveData<List<String>> get() = _likedMatchIds
    private val _likedMatchIds = MutableLiveData<List<String>>().apply { value = emptyList() }

    private var lastRefresh: Long = -1

    override suspend fun refreshMatches() {
        val currentTime: Long = currentTimeInSeconds()
        val timeSinceRefresh: Long = lastRefresh - currentTime
        if (_availableMatches.value.isNullOrEmpty() || timeSinceRefresh > MAX_TIME_SINCE_REFRESH) {
            lastRefresh = currentTime
            val matches: List<Match> = withContext(Dispatchers.IO) {
                matchesService.getMatches().data
            }
            _availableMatches.postValue(matches)
        }
    }

    private fun currentTimeInSeconds(): Long {
        return System.currentTimeMillis() / MILLIS_PER_SECOND
    }

    override fun updateLiked(userId: String) {
        val newLiked: List<String> = _likedMatchIds.value?.let { selectedMatchIds ->
            if (selectedMatchIds.contains(userId)) {
                selectedMatchIds.filterNot { it == userId }
            } else {
                selectedMatchIds + userId
            }
        } ?: emptyList()

        _likedMatchIds.postValue(newLiked)
    }

    companion object {
        const val MILLIS_PER_SECOND = 1000L
        const val MAX_TIME_SINCE_REFRESH = 300
    }
}

