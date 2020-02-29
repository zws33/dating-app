package me.zwsmith.datingapp.ui.matches

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.zwsmith.datingapp.R
import me.zwsmith.datingapp.ui.matches.matchpercent.MatchPercentFragment
import me.zwsmith.datingapp.ui.matches.specialblend.SpecialBlendFragment

class MatchesPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> SpecialBlendFragment.newInstance()
            1 -> MatchPercentFragment.newInstance()
            else -> throw IllegalStateException("Could not get Fragment for position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 2

    companion object {
        private val TAB_TITLES = arrayOf(
            R.string.special_blend,
            R.string.math_percent
        )
    }
}