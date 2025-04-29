package com.example.feature_tvbox.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_tvbox.databinding.FragmentTvboxSiteListBinding
import com.example.lib_database.DatabaseManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for displaying a list of all TVBox sites.
 * Follows clean architecture and Kotlin best practices.
 */
class TvBoxSiteListFragment : Fragment() {
    private var _binding: FragmentTvboxSiteListBinding? = null
    private val binding: FragmentTvboxSiteListBinding get() = _binding!!

    private val siteListViewModel: TvBoxSiteListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val databaseManager: DatabaseManager = DatabaseManager.getInstance(requireContext().applicationContext)
                @Suppress("UNCHECKED_CAST")
                return TvBoxSiteListViewModel(databaseManager) as T
            }
        }
    }

    private val siteAdapter: TvBoxSiteAdapter = TvBoxSiteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvboxSiteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeSiteList()
        siteListViewModel.loadSites()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = siteAdapter
        }
    }

    private fun observeSiteList() {
        viewLifecycleOwner.lifecycleScope.launch {
            siteListViewModel.siteList.collectLatest { siteList ->
                siteAdapter.submitList(siteList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 