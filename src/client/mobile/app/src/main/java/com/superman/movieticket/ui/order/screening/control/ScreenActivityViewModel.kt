package com.superman.movieticket.ui.order.screening.control

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.Screening
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ScreenActivityViewModel : ViewModel() {

    private  var _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private  val _listAllScreen= mutableStateOf<List<Screening>>(emptyList())
    var listAllScreen : State<List<Screening>> =_listAllScreen
    init{
        viewModelScope.launch {
            //getListScreen
        }
    }
    fun setDate(localDate: LocalDate) {
        _date.value= localDate.toString()
    }

}

// private val _movie = mutableStateOf<Movie?>(null)
// var movie by _movie
//    private set

//private val _movie = MutableLiveData<Movie>()
//val movie: LiveData<Movie> get() = _movie