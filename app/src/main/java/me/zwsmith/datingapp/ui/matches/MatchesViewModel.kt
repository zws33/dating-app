package me.zwsmith.datingapp.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.zwsmith.datingapp.domain.Repository

class MatchesViewModel(private val matchesRepository: Repository) : ViewModel() {

    val viewStates: LiveData<List<MatchItemViewState>> get() = _viewStates
    private val _viewStates = MutableLiveData<List<MatchItemViewState>>()

    fun loadMatches() {
        _viewStates.value = (0..5).map {
            MatchItemViewState(
                username = "asianbluedust",
                age = "29",
                city = "Teaneck",
                stateCode = "NJ",
                matchPercent = "47",
                imageUrl = "https://i.pinimg.com/originals/24/6d/5d/246d5dbabe52ebd7ce807f2f293ee892.jpg"
            )
        }
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