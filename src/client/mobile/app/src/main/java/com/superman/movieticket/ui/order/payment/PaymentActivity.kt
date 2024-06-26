@file:OptIn(ExperimentalMaterial3Api::class)

package com.superman.movieticket.ui.order.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceControl.Transaction
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.getStringFromSharedPreferences
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.main.hooks.NavigateMainActivity
import com.superman.movieticket.ui.order.food.control.OrderFoodActivityModel
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.model.ReservationExtendModel
import com.superman.movieticket.ui.order.payment.control.PaymentActivityViewModel
import com.superman.movieticket.ui.theme.balooFont
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PaymentActivity : ComponentActivity() {

    private lateinit var hubConnection: HubConnection
    val paymentActivityViewModel: PaymentActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        val reservationCreateModel = Gson().fromJson(
            intent.getStringExtra("ReservationCreateModel"),
            ReservationCreateModel::class.java
        )
        Log.d("reservation", reservationCreateModel.toString())
        setContent {
            val transactionId = paymentActivityViewModel.transactionStatus.collectAsState()
            transactionId.value?.let { connectToHub(it, reservationCreateModel) }

            BaseScreen(content = {
                PaymentComp(
                    Gson().fromJson(
                        intent.getStringExtra("TotalPrice"),
                        ReservationExtendModel::class.java
                    )
                )
            }, title = "Thanh toán vé phim", onNavigateUp = { finish() })
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectToHub(
        transactionId: String,
        reservationCreateModel: ReservationCreateModel
    ) {
        hubConnection =
            HubConnectionBuilder.create("${AppOptions.BASE_URL}/client-hub/paymentHub?transactionId=${transactionId}")
                .shouldSkipNegotiate(true).build()

        lifecycleScope.launch {
            try {
                hubConnection.start().blockingAwait()
                if (hubConnection.connectionState == HubConnectionState.CONNECTED) {
                    hubConnection.on("ConfirmPayment", { confirmedTransaction: String ->
                        Log.d("Chinh ", "Payment confirmed: ${confirmedTransaction}")
                        runOnUiThread {
                            Toast.makeText(
                                this@PaymentActivity,
                                "Thanh toán thành công",
                                Toast.LENGTH_LONG
                            ).show()
                            paymentActivityViewModel.HandleCreateReservationAsync(
                                ReservationCreateModel(
                                    screeningId = reservationCreateModel.screeningId,
                                    reservationState = 0,
                                    seatReservations = reservationCreateModel.seatReservations,
                                    serviceReservations = reservationCreateModel.serviceReservations,
                                    transactionId = confirmedTransaction
                                )
                            )
                            val intent = Intent(this@PaymentActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }, String::class.java)
                }

            } catch (e: Exception) {
                Log.e("BalanceChangeReceiver", "Connection error: ${e.message}", e)
            }
        }

        // Monitor the connection state
        hubConnection.onClosed {
        }
    }


}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PaymentComp(
    reservationExtendModel: ReservationExtendModel
) {
    var showTooltip by remember { mutableStateOf(false) }
    var tooltipText by remember { mutableStateOf("Sao chép STK") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val contentPayment = remember {
        mutableStateOf("")
    }

    val paymentActivityViewModel: PaymentActivityViewModel = hiltViewModel()
    LaunchedEffect(key1 = Unit) {

        paymentActivityViewModel.HandleCreateTransactionAsync(reservationExtendModel.total) {
            contentPayment.value = it
        }
        Log.d("TotalPrice", reservationExtendModel.total.toString())

    }

    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Tổng số tiền thanh toán",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
                Row {
                    Text(
                        text = "${reservationExtendModel.total.toString()}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }

            }
        }


        item {
            Image(
                painter = painterResource(id = R.drawable.img_4),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(300.dp)
                    .clickable {
                        showTooltip = true
                        scope.launch {
                            delay(2000)
                            showTooltip = false
                        }
                    }
            )


            if (showTooltip) {
                Popup(
                    alignment = Alignment.TopCenter,
                    onDismissRequest = { showTooltip = false }
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                copyToClipboard(context, "109875071116")
                                tooltipText = "Đã sao chép"
                                scope.launch {
                                    delay(1500)
                                    showTooltip = false
                                    tooltipText = "Sao chép STK"
                                }
                            }
                    ) {
                        Text(
                            text = tooltipText,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.background),

                            )
                    }
                }
            }
        }

        item {

            Column(modifier = Modifier.apply {
                padding(10.dp)
            }) {

                Button(onClick = { copyToClipboard(context, contentPayment.value) }) {
                    Text(text = "Sao chép nội dung chuyển khoản")
                }
            }
        }


    }


}








