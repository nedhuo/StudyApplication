package com.example.feature_tvbox.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_tvbox.data.SiteSyncRepository
import com.example.feature_tvbox.databinding.FragmentTvboxListBinding
import com.example.feature_tvbox.player.TvBoxPlayerActivity
import com.example.lib_base.widget.LoadingDialog
import com.example.lib_database.DatabaseManager
import com.example.lib_spider.meowtv.spider.MeowTvSpider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TvBoxListFragment : Fragment() {
    private var _binding: FragmentTvboxListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TvBoxListViewModel by viewModels {
        // 你需要实现自己的 ViewModelFactory 注入 siteRepository、videoDao、application
        TvBoxListViewModelFactory(requireActivity().application)
    }
    private val adapter = TvBoxVideoAdapter { video ->
        // 点击跳转播放
        startActivity(TvBoxPlayerActivity.newIntent(requireContext(), video.url, video.title))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvboxListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            showAndParse()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.videoList.collectLatest {
                adapter.submitList(it)
            }
        }
        // 首次自动刷新
        showAndParse()
    }

    private fun showAndParse() {
        val loading = LoadingDialog.show(childFragmentManager, "正在解析...")
        val databaseManager = DatabaseManager.getInstance(requireContext().applicationContext)
        val siteSyncRepository = SiteSyncRepository(databaseManager, MeowTvSpider)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.syncSitesAndFetchVideos(siteSyncRepository)
            loading.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 