package me.zwsmith.datingapp.ui.matches.specialblend

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.zwsmith.datingapp.common.zip
import me.zwsmith.datingapp.data.Match
import me.zwsmith.datingapp.domain.Repository
import me.zwsmith.datingapp.ui.matches.MatchItemViewState
import me.zwsmith.datingapp.ui.matches.MatchesViewState

class SpecialBlendViewModel(private val matchesRepository: Repository) : ViewModel() {

    val specialBlendViewStates: LiveData<MatchesViewState> =
        zip(
            matchesRepository.availableMatches,
            matchesRepository.likedMatchIds
        ) { matches, ids ->
            getSpecialBlendViewState(matches, ids, ::updateLiked)
        }

    fun refreshMatches() {
        viewModelScope.launch {
            matchesRepository.refreshMatches()
        }
    }

    private fun updateLiked(userId: String) {
        matchesRepository.updateLiked(userId)
    }

    private fun getSpecialBlendViewState(
        list: List<Match>,
        likedIds: List<String>,
        onClick: (String) -> Unit
    ): MatchesViewState {
        val matches: List<MatchItemViewState> = list.map { matchData ->
            MatchItemViewState(
                username = matchData.username,
                age = matchData.age.toString(),
                city = matchData.location.cityName,
                stateCode = matchData.stateCode,
                matchPercent = (matchData.match / 100).toString(),
                imageUrl = matchData.photo.fullPaths.large,
                isSelected = likedIds.contains(matchData.userid),
                onClick = { onClick(matchData.userid) }
            )
        }

        return MatchesViewState(
            isProgressVisible = list.isEmpty(),
            matches = matches
        )
    }
}

