package com.innovaocean.adoptmeapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.databinding.FragmentHomeBinding
import com.innovaocean.adoptmeapp.domain.Breed
import com.innovaocean.adoptmeapp.util.gone
import com.innovaocean.adoptmeapp.util.viewBinding
import com.innovaocean.adoptmeapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var breedAdapter: BreedAdapter
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()

        viewModel.searchBreeds.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BreedEvent.Success -> {
                    binding.progressBar.gone()
                    binding.errorText.gone()
                    breedAdapter.differ.submitList(response.list)
                }
                is BreedEvent.Error -> {
                    binding.errorText.visible()
                    binding.errorText.text = response.error
                    breedAdapter.differ.submitList(emptyList())
                    binding.progressBar.gone()
                }
                is BreedEvent.Loading -> {
                    binding.errorText.gone()
                    binding.progressBar.visible()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        breedAdapter = BreedAdapter {
            onBreedClicked(it)
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
}