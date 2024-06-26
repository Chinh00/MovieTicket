package com.superman.movieticket.ui.order.ticket.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.signalr.HubConnection
import com.superman.movieticket.domain.entities.Room
import com.superman.movieticket.domain.entities.Seat
import com.superman.movieticket.domain.services.RoomService
import com.superman.movieticket.infrastructure.di.CoroutineScopeDefault
import com.superman.movieticket.infrastructure.networks.FilterModel
import com.superman.movieticket.infrastructure.networks.defaultXQueryHeader
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.ListResponse
import com.superman.movieticket.infrastructure.utils.SuccessResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class BookTicketViewModel @Inject constructor(
    @CoroutineScopeDefault private val coroutineScope: CoroutineScope,
    private val hubConnection: HubConnection,
    private val roomService: RoomService
) : ViewModel() {
    val _apiState = MutableStateFlow(ApiState.NONE)
    val apiState = _apiState.asStateFlow()

    val _roomState = MutableStateFlow(Room(0, emptyList<Seat>()))
    val roomState = _roomState.asStateFlow()


    init {

    }



    fun GetAllSeatsOfRoomAsync(
        roomId: String,
        screeningId: String
    ) {
        _apiState.value = ApiState.LOADING
        roomService.HandleGetRoomByIdAsync(roomId, screeningId).enqueue(object :
            Callback<SuccessResponse<Room>> {
            override fun onResponse(
                call: Call<SuccessResponse<Room>>,
                response: Response<SuccessResponse<Room>>
            ) {
                _roomState.value = response.body()?.data!!
                Log.d("Chinh", response.body().toString())
                _apiState.value = ApiState.SUCCESS
            }

            override fun onFailure(call: Call<SuccessResponse<Room>>, t: Throwable) {
                _apiState.value = ApiState.FAIL
                Log.d("Fail", t.message.toString())
            }
        })
        _apiState.value = ApiState.NONE
    }
}