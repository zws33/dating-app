package me.zwsmith.datingapp.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.zwsmith.datingapp.data.MatchesResponse
import me.zwsmith.datingapp.domain.Repository

class MatchesViewModel(private val matchesRepository: Repository) : ViewModel() {

    val viewStates: LiveData<MatchesViewState> get() = _viewStates
    private val _viewStates = MutableLiveData<MatchesViewState>()

    fun loadMatches() {
        viewModelScope.launch {
            _viewStates.postValue(MatchesViewState.LOADING)
            val matches: MatchesViewState = withContext(Dispatchers.IO) {
                matchesRepository.getMatches().toMatchesViewState()
            }
            _viewStates.postValue(matches)
        }
    }
}

fun MatchesResponse.toMatchesViewState(): MatchesViewState {
    val matches: List<MatchItemViewState> = data.map { data ->
        MatchItemViewState(
            username = data.username,
            age = data.age.toString(),
            city = data.location.cityName,
            stateCode = data.stateCode,
            matchPercent = (data.match / 100).toString(),
            imageUrl = data.photo.fullPaths.large
        )
    }

    return MatchesViewState(isProgressVisible = data.isEmpty(), matches = matches)
}

