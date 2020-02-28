package me.zwsmith.datingapp.ui.matches

data class MatchItemViewState(
    val username: String,
    val age: String,
    val city: String,
    val stateCode: String,
    val matchPercent: String,
    val imageUrl: String,
    val isSelected: Boolean
)

data class MatchesViewState(
    val isProgressVisible: Boolean,
    val matches: List<MatchItemViewState>
) {
    companion object {
        val LOADING = MatchesViewState(
            isProgressVisible = true,
            matches = emptyList()
        )
    }
}