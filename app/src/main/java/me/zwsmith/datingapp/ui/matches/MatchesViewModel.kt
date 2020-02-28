package me.zwsmith.datingapp.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.zwsmith.datingapp.domain.Match
import me.zwsmith.datingapp.domain.Repository

class MatchesViewModel(private val matchesRepository: Repository) : ViewModel() {

    val viewStates: LiveData<MatchesViewState> = Transformations.map(matchesRepository.selectedMatches) {
        it.toMatchesViewState()
    }
    private val _viewStates = MutableLiveData<MatchesViewState>()

    fun loadMatches() {
        viewModelScope.launch {
            _viewStates.postValue(MatchesViewState.LOADING)
            matchesRepository.getMatches()
        }
    }
}

fun List<Pair<Match, Boolean>>.toMatchesViewState(): MatchesViewState {
    val matches: List<MatchItemViewState> = map { data ->
        MatchItemViewState(
            username = data.first.username,
            age = data.first.age.toString(),
            city = data.first.location.cityName,
            stateCode = data.first.stateCode,
            matchPercent = (data.first.match / 100).toString(),
            imageUrl = data.first.photo.fullPaths.large,
            isSelected = data.second
        )
    }

    return MatchesViewState(isProgressVisible = this.isEmpty(), matches = matches)
}

