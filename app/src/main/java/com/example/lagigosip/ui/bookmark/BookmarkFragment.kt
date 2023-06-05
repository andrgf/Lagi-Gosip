package com.example.lagigosip.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lagigosip.databinding.FragmentBookmarkBinding
import com.example.lagigosip.ui.adapter.NewsAdapter
import com.example.lagigosip.ui.home.ViewModelFactory

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: BookmarkViewModel by viewModels {
            factory
        }

        newsAdapter = NewsAdapter {news ->
            if (news.isBookmarked) {
                viewModel.delete(news)
            }
        }

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        viewModel.getBookmark().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}