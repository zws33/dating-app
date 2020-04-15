package me.zwsmith.datingapp.ui.matches

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import me.zwsmith.datingapp.R

@BindingAdapter("isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("useYellowBackgroundColor")
fun useYellowBackgroundColor(view: ViewGroup, isYellow: Boolean) {
    view.setBackgroundResource(if (isYellow) R.color.yellow else R.color.colorAccent)
}

@BindingAdapter("matchItemMatchPercentText")
fun matchPercentText(view: TextView, matchPercent: String) {
    view.text = view.context.getString(R.string.match_percent, matchPercent)
}

@BindingAdapter("matchItemImageResource")
fun setImageResource(imageView: ImageView, imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .fit()
        .centerCrop()
        .placeholder(R.drawable.ic_person)
        .into(imageView)
}

@BindingAdapter("city", "stateCode")
fun locationString(view: TextView, city: String, stateCode: String) {
    view.text = view.context.getString(R.string.location, city, stateCode)
}