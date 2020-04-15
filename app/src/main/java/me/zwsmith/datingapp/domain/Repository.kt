package me.zwsmith.datingapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.zwsmith.datingapp.data.Match
import me.zwsmith.datingapp.data.MatchesService
import java.util.*

interface Repository {
    suspend fun refreshMatches()
    fun updateLiked(userId: String)
    val availableMatches: LiveData<List<Match>>
    val likes: LiveData<List<Like>>
    fun cancelLiked(userId: String)
}

class RepositoryImpl(private val matchesService: MatchesService) : Repository {

    override val availableMatches: LiveData<List<Match>> get() = _availableMatches
    private val _availableMatches = MutableLiveData<List<Match>>().apply { value = emptyList() }

    override val likes: LiveData<List<Like>> get() = _likes
    private val _likes = MutableLiveData<List<Like>>().apply { value = emptyList() }

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
        _likes.value?.let { likes ->
            if (likes.any { it.id == userId && !it.isPending }) {
                _likes.postValue(likes.filterNot { it.id == userId })
            } else {
                addLiked(userId)
                _likes.postValue(likes + Like(id = userId, isPending = true))
            }
        }
    }

    private val pending: MutableMap<String, Timer> = mutableMapOf()
    private fun addLiked(userId: String) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                _likes.value?.let { likes ->
                    val updatedLikes = likes.map {
                        if (it.id == userId && it.isPending) {
                            Like(id = it.id, isPending = !it.isPending)
                        } else {
                            it
                        }
                    }
                    _likes.postValue(updatedLikes)
                }
            }
        }, DELAY_IN_SECONDS * MILLIS_PER_SECOND)

        pending[userId] = timer
    }

    override fun cancelLiked(userId: String) {
        val timer: Timer? = pending[userId]
        timer?.cancel()
        _likes.value?.let { likes ->
            _likes.postValue(likes.filterNot { it.id == userId })
        }
    }

    companion object {
        const val MILLIS_PER_SECOND = 1000L
        const val MAX_TIME_SINCE_REFRESH = 300
        const val DELAY_IN_SECONDS = 5
    }
}

data class Like(val id: String, val isPending: Boolean)

