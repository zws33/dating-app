package me.zwsmith.datingapp.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.zwsmith.datingapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A placeholder fragment containing a simple view.
 */
class MatchesFragment : Fragment() {

    private val matchesViewModel: MatchesViewModel by viewModel()

    private val matchesListAdapter: MatchListAdapter by lazy { MatchListAdapter(requireContext()) }
    private lateinit var matchesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchesViewModel.loadMatches()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initUi(view)
        return view
    }

    private fun initUi(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        matchesList = view.findViewById(R.id.matches_rv)
        matchesList.adapter = matchesListAdapter
        matchesList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesViewModel.viewStates.observe(viewLifecycleOwner, Observer { viewState ->
            progressBar.isVisible = viewState.isProgressVisible
            matchesListAdapter.matchesData = viewState.matches
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

