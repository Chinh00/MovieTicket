package com.superman.movieticket.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout

import com.superman.movieticket.core.view.FragmentBase
import com.superman.movieticket.core.view.FragmentWithComposeBase
import com.superman.movieticket.databinding.FragmentNewsBinding
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.news.control.NewsFragmentImpl
import com.superman.movieticket.ui.news.control.NewsFragmentModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : FragmentWithComposeBase<NewsFragmentImpl>() {
    override fun getViewModel() = NewsFragmentImpl::class.java


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "")
            }
        }
    }

}
