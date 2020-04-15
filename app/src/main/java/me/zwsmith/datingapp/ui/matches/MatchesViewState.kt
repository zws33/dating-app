package me.zwsmith.datingapp.ui.matches

data class MatchesViewState(
    val isProgressVisible: Boolean,
    val matches: List<MatchItemViewState>
)

data class MatchItemViewState(
    val username: String,
    val age: String,
    val city: String,
    val stateCode: String,
    val matchPercent: String,
    val imageUrl: String,
    val isYellow: Boolean,
    val isCancelVisible: Boolean,
    val onClick: () -> Unit,
    val onCancelClick: () -> Unit
) {
    fun onClick() {
        onClick.invoke()
    }

    fun onCancelClick() {
        onCancelClick.invoke()
    }
}