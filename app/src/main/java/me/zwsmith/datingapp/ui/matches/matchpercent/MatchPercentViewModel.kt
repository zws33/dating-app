package me.zwsmith.datingapp.ui.matches.matchpercent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.zwsmith.datingapp.common.zip
import me.zwsmith.datingapp.data.Match
import me.zwsmith.datingapp.domain.Repository
import me.zwsmith.datingapp.ui.matches.MatchItemViewState
import me.zwsmith.datingapp.ui.matches.MatchesViewState

class MatchPercentViewModel(private val matchesRepository: Repository) : ViewModel() {


    val matchPercentViewStates: LiveData<MatchesViewState> =
        zip(
            matchesRepository.availableMatches,
            matchesRepository.likes
        ) { matches, likes ->
            val likedMatches = matches
                .filter { likes.map { like -> like.id }.contains(it.userid) }
                .sortedByDescending { it.match }
            getMatchPercentViewState(likedMatches, ::updateLiked)
        }

    private fun updateLiked(userId: String) {
        matchesRepository.updateLiked(userId)
    }

    private fun getMatchPercentViewState(
        list: List<Match>,
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
                isYellow = true,
                isCancelVisible = false,
                onClick = { onClick(matchData.userid) },
                onCancelClick = { }
            )
        }

        return MatchesViewState(
            isProgressVisible = false,
            matches = matches
        )
    }

    companion object {
        val TAG = MatchPercentViewModel::class.java.simpleName
    }

}