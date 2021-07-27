package com.innovaocean.adoptmeapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.data.BreedResponse
import com.innovaocean.adoptmeapp.util.Status
import com.innovaocean.adoptmeapp.util.gone
import com.innovaocean.adoptmeapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var breedAdapter: BreedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView(view)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val errorText = view.findViewById<TextView>(R.id.errorText)

        viewModel.searchBreeds.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    progressBar.gone()
                    errorText.gone()
                    breedAdapter.differ.submitList(response.data?.toList())
                }
                Status.ERROR -> {
                    errorText.visible()
                    errorText.text = response.message.toString()
                    breedAdapter.differ.submitList(emptyList())
                    progressBar.gone()
                }
                Status.LOADING -> {
                    errorText.gone()
                    progressBar.visible()
                }
            }
        })
    }

    private fun setupRecyclerView(view: View) {
        breedAdapter = BreedAdapter {
            onBreedClicked(it)
        }
        view.findViewById<RecyclerView>(R.id.breedsList).apply {
            adapter = breedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onBreedClicked(response: BreedResponse) {
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
                if (viewModel.searchedQuery.isEmpty()) "Balinese" else viewModel.searchedQuery
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