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

    val viewStates: LiveData<List<MatchItemViewState>> get() = _viewStates
    private val _viewStates = MutableLiveData<List<MatchItemViewState>>()

    fun loadMatches() {
        viewModelScope.launch {
            val matches: List<MatchItemViewState> = withContext(Dispatchers.IO) {
                matchesRepository.getMatches().toMatchesViewState()
            }
            _viewStates.postValue(matches)
        }
    }
}

fun MatchesResponse.toMatchesViewState(): List<MatchItemViewState> {
    return data.map { data ->
        MatchItemViewState(
            username = data.username,
            age = data.age.toString(),
            city = data.location.city_name,
            stateCode = data.state_code,
            matchPercent = (data.match / 100).toString(),
            imageUrl = data.photo.full_paths.large

        )
    }
}

data class MatchItemViewState(
    val username: String,
    val age: String,
    val city: String,
    val stateCode: String,
    val matchPercent: String,
    val imageUrl: String
)