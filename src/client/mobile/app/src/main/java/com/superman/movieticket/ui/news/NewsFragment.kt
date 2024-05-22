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

<<<<<<< HEAD
    override fun setupViews() {
    }

    override fun setupActions() {
    }
=======
>>>>>>> 644d0811f51bf486f7675193a353940b88ac2a2c

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
<<<<<<< HEAD
                AppBarCustom()
=======
                AppWidgetCustom()
>>>>>>> 644d0811f51bf486f7675193a353940b88ac2a2c
            }
        }
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
<<<<<<< HEAD
fun AppBarCustom() {
    val context = LocalContext.current
    Button(onClick = {Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show()}){
        Text(text = "Click")
=======
fun AppWidgetCustom() {
    return ConstraintLayout(modifier = Modifier.apply {
        padding(50.dp)
    }) {

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Click ...")
        }
>>>>>>> 644d0811f51bf486f7675193a353940b88ac2a2c
    }
}
