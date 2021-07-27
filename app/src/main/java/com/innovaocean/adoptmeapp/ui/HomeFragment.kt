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
import androidx.recyclerview.widget.RecyclerView
import com.innovaocean.adoptmeapp.R
import com.innovaocean.adoptmeapp.data.BreedResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var breedAdapter: BreedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView(view)

        viewModel.searchBreeds.observe(viewLifecycleOwner, { response ->
            breedAdapter.differ.submitList(response.data?.toList())
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
        //todo add bundle response here.
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
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
            //todo add filtered texts here
            viewModel.searchBreeds(text)

        }
        return true
    }
}