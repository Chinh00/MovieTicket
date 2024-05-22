package com.superman.movieticket.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppBarCustom()
            }
        }
    }


}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppBarCustom() {
    val context = LocalContext.current
    Button(onClick = {Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show()}){
        Text(text = "Click")
    }
}