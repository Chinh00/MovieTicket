package com.superman.movieticket.ui.news

import com.superman.movieticket.core.view.FragmentBase
import com.superman.movieticket.databinding.FragmentNewsBinding
import com.superman.movieticket.ui.news.control.NewsFragmentImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : FragmentBase<FragmentNewsBinding, NewsFragmentImpl>() {
    override fun getViewBinding() = FragmentNewsBinding.inflate(layoutInflater)

    override fun getViewModel() = NewsFragmentImpl::class.java

    override fun setupViews() {
    }

    override fun setupActions() {
    }

}