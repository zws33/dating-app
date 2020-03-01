package me.zwsmith.datingapp.ui.matches.specialblend

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.zwsmith.datingapp.R
import me.zwsmith.datingapp.ui.matches.MatchListAdapter
import me.zwsmith.datingapp.ui.matches.MatchesViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpecialBlendFragment : Fragment() {

    private val specialBlendViewModel: SpecialBlendViewModel by viewModel()

    private val matchesListAdapter: MatchListAdapter by lazy { MatchListAdapter(requireContext()) }
    private lateinit var matchesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        specialBlendViewModel.refreshMatches()
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
        matchesList = view.findViewById<RecyclerView>(R.id.matches_rv).apply {
            adapter = matchesListAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialBlendViewModel.specialBlendViewStates.observe(
            viewLifecycleOwner,
            Observer { viewState -> render(viewState) }
        )
    }

    private fun render(viewState: MatchesViewState) {
        matchesListAdapter.matchesData = viewState.matches
        if (viewState.isProgressVisible) {
            progressBar.animate().alpha(1.0f).setDuration(250).onAnimationStart {
                progressBar.isVisible = viewState.isProgressVisible
            }
            matchesList.animate().alpha(0.0f).setDuration(500).onAnimationEnd {
                matchesList.isVisible = !viewState.isProgressVisible
            }
        } else {
            matchesList.animate().alpha(1.0f).setDuration(500).onAnimationStart {
                matchesList.isVisible = !viewState.isProgressVisible
            }
            progressBar.animate().alpha(0.0f).setDuration(250).onAnimationEnd {
                progressBar.isVisible = viewState.isProgressVisible
            }
        }
    }

    private fun ViewPropertyAnimator.onAnimationEnd(onEnd: () -> Unit) {
        setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(p0: Animator?) {
                onEnd()
            }
        })
    }

    private fun ViewPropertyAnimator.onAnimationStart(onStart: () -> Unit) {
        setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(p0: Animator?) {
                onStart()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): SpecialBlendFragment {
            return SpecialBlendFragment()
        }
    }
}

