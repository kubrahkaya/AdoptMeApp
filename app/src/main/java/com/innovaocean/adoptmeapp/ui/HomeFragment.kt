package com.innovaocean.adoptmeapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.databinding.FragmentHomeBinding
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var breedAdapter: BreedAdapter

    private var stateJob: Job? = null
    private var eventJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()

        observeStates()
        observeEvents()
    }

    private fun observeStates() {
        stateJob = lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.progressBar.isVisible = state.isLoading
                breedAdapter.differ.submitList(state.breedList)
            }
        }
    }

    private fun observeEvents() {
        eventJob = lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is BreedsEvent.ShowError -> {
                        showSnackBar(event.message)
                        breedAdapter.differ.submitList(emptyList())
                    }
                    is BreedsEvent.OpenBreedDetail -> {
                        onBreedClicked(event.breed)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        breedAdapter = BreedAdapter {
            viewModel.onBreedClicked(it)
        }
        binding.breedsList.apply {
            adapter = breedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onBreedClicked(response: Breed) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                response
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            isIconifiedByDefault = false
            queryHint = "Search"
            val query =
                viewModel.searchedQuery.ifEmpty { "Balinese" }
            onQueryTextChange(query)
            setQuery(query, true)
            minimumWidth = 2700000
            isSubmitButtonEnabled = true
        }.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let {
            viewModel.searchedQuery = text
            viewModel.searchBreeds(text)
        }
        if (text.isNullOrEmpty()) {
            breedAdapter.differ.submitList(emptyList())
        }
        return true
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        stateJob?.cancel()
        eventJob?.cancel()
        super.onDestroyView()
    }
}