package me.zwsmith.datingapp.ui.matches.matchpercent

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
import me.zwsmith.datingapp.ui.matches.MatchListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchPercentFragment : Fragment() {

    private val matchesViewModel: MatchPercentViewModel by viewModel()

    private val matchesListAdapter: MatchListAdapter by lazy {
        MatchListAdapter(
            requireContext()
        )
    }
    private lateinit var matchesList: RecyclerView
    private lateinit var progressBar: ProgressBar

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
        matchesViewModel.matchPercentViewStates.observe(viewLifecycleOwner, Observer { viewState ->
            matchesListAdapter.matchesData = viewState.matches
            progressBar.isVisible = viewState.isProgressVisible
            matchesList.isVisible = !viewState.isProgressVisible
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): MatchPercentFragment {
            return MatchPercentFragment()
        }
    }
}