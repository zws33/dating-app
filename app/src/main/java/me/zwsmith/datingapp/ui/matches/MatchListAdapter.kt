package me.zwsmith.datingapp.ui.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.zwsmith.datingapp.R

class MatchListAdapter(
    private val context: Context
) : RecyclerView.Adapter<MatchListAdapter.MatchItem>() {
    var matchesData: List<MatchItemViewState> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MatchItem(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username_tv)
        val age: TextView = view.findViewById(R.id.age_tv)
        val location: TextView = view.findViewById(R.id.location_tv)
        val matchPercent: TextView = view.findViewById(R.id.match_percent_tv)
        val profilePhoto: AppCompatImageView = view.findViewById(R.id.profile_photo_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_card, parent, false)
        return MatchItem(view)
    }

    override fun getItemCount(): Int {
        return matchesData.size
    }

    override fun onBindViewHolder(holder: MatchItem, position: Int) {
        val viewState: MatchItemViewState = matchesData[position]
        with(holder) {
            username.text = viewState.username
            age.text = viewState.age
            location.text = getLocationString(viewState)
            matchPercent.text = context.getString(R.string.match_percent, viewState.matchPercent)
            val picasso = Picasso.get().apply {
                isLoggingEnabled = true
            }
            picasso
                .load(viewState.imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_person)
                .into(profilePhoto)
        }
    }

    private fun getLocationString(viewState: MatchItemViewState) =
        context.getString(R.string.location, viewState.city, viewState.stateCode)
}