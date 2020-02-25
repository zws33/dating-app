package me.zwsmith.datingapp.ui.matches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.zwsmith.datingapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A placeholder fragment containing a simple view.
 */
class MatchesFragment : Fragment() {

    private val matchesViewModel: MatchesViewModel by viewModel()

    private val matchesListAdapter: MatchListAdapter by lazy { MatchListAdapter(requireContext()) }
    private lateinit var matchesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchesViewModel.loadMatches()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        matchesList = root.findViewById(R.id.matches_rv)
        matchesList.adapter = matchesListAdapter
        matchesList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesViewModel.viewStates.observe(viewLifecycleOwner, Observer { data ->
            matchesListAdapter.matchesData = data
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): MatchesFragment {
            return MatchesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}

class MatchListAdapter(
    private val context: Context
): RecyclerView.Adapter<MatchListAdapter.MatchItem>() {
    var matchesData: List<MatchItemViewState> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MatchItem(view: View): RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username_tv)
        val age: TextView = view.findViewById(R.id.age_tv)
        val location: TextView = view.findViewById(R.id.location_tv)
        val matchPercent: TextView = view.findViewById(R.id.match_percent_tv)
        val profilePhoto: AppCompatImageView = view.findViewById(R.id.profile_photo_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_card, parent, false)
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
                .into(profilePhoto)
        }
    }

    private fun getLocationString(viewState: MatchItemViewState) =
        context.getString(R.string.location, viewState.city, viewState.stateCode)
}