package com.superman.movieticket.ui.order.ticket.control

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.signalr.HubConnection
import com.superman.movieticket.domain.entities.Room
import com.superman.movieticket.domain.entities.Seat
import com.superman.movieticket.domain.services.RoomService
import com.superman.movieticket.domain.services.SeatPlaceService
import com.superman.movieticket.domain.services.SeatState
import com.superman.movieticket.domain.services.SeatStateCreateModel
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
    private val roomService: RoomService,
    private val seatPlaceService: SeatPlaceService
) : ViewModel() {
    val _apiState = MutableStateFlow(ApiState.NONE)
    val apiState = _apiState.asStateFlow()

    val _roomState = MutableStateFlow(Room(0, emptyList<Seat>()))
    val roomState = _roomState.asStateFlow()

    val _seatState = MutableStateFlow<SeatState?>(null)
    val seatState = _seatState.asStateFlow()

    val _lockId = MutableStateFlow(mutableListOf<String>())
    val lockId = _lockId.asStateFlow()

    val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    init {

    }


    fun HandleGetSeatState(roomId: String, screeningId: String, seatId: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            seatPlaceService.HandleGetSeatState(screeningId, roomId, seatId).enqueue(object: Callback<SeatState> {
                override fun onResponse(call: Call<SeatState>, response: Response<SeatState>) {

                    if (response?.body() == null) {
                        onSuccess(true)

                        Log.d("Locking", "Ghế chưa bị khoá ${response?.body()}}")
                    } else {
                        Log.d("Locking", "Ghế đã bị khoá")
                        onSuccess(false)
                    }

                }

                override fun onFailure(call: Call<SeatState>, t: Throwable) {
                }
            })

        }
    }

    fun HandleRegisterSeatPlace (roomId: String, screeningId: String, seatId: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            seatPlaceService.HandleLockSeatState(SeatStateCreateModel(roomId, screeningId, seatId)).enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    onSuccess(response?.body()!!)
                    _lockId.value.add(response?.body()!!)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    onSuccess("")

                }

            })

        }
    }

    fun HandleUnLockSeatPlace(lockId: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            seatPlaceService.HandleUnLockSeatState(lockId).enqueue(object: Callback<SeatState> {
                override fun onResponse(call: Call<SeatState>, response: Response<SeatState>) {
                    _lockId.value.clear()
                }

                override fun onFailure(call: Call<SeatState>, t: Throwable) {
                }
            })
        }
    }
    

    fun GetAllSeatsOfRoomAsync(
        roomId: String,
        screeningId: String
    ) {
        viewModelScope.launch {
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


    fun GetAllSeatsOfRoomAsyncWithCallBack(
        roomId: String,
        screeningId: String,
        onSuccess: (Room) -> Unit
    ) {
        viewModelScope.launch {
            roomService.HandleGetRoomByIdAsync(roomId, screeningId).enqueue(object :
                Callback<SuccessResponse<Room>> {
                override fun onResponse(
                    call: Call<SuccessResponse<Room>>,
                    response: Response<SuccessResponse<Room>>
                ) {
                    onSuccess(response?.body()?.data!!)
                }

                override fun onFailure(call: Call<SuccessResponse<Room>>, t: Throwable) {
                    _apiState.value = ApiState.FAIL
                    Log.d("Fail", t.message.toString())
                }
            })
        }
    }
}