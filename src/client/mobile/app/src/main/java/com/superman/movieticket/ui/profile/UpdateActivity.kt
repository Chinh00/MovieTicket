package com.superman.movieticket.ui.profile

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.User
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.auth.control.PhoneOtpActivityViewModel
import com.superman.movieticket.ui.profile.control.UpdateActivityViewModel
import com.superman.movieticket.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class UpdateActivity : ComponentActivity() {

    private val updateActivityViewModel: UpdateActivityViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = updateActivityViewModel.userinfo.collectAsState().value
            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    if (user != null) {
                        UpdateScreen(user) {

                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateScreen(
    user: User,
    onUpdateUser: (User) -> Unit
) {
    var name by remember { mutableStateOf(user.fullName) }
    var email by remember { mutableStateOf(user.email ?: "") }
    var phone by remember { mutableStateOf(user.phoneNumber) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar), // Thay đổi ID ảnh tương ứng
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                TextField(
                    value = name, colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    onValueChange = { name = it },
                    label = { Text(text = "Họ tên")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                )


            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                /*GenderDropdown(
                    onClick = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                )*/

                /*DatePickerDropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    defaultDate = birthDate
                ) {
                    birthDate = it
                }*/
            }



            Spacer(modifier = Modifier.height(10.dp))



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                TextField(
                    value = phone, colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    readOnly = true,
                    onValueChange = { },
                    label = { Text(text = "Số điện thoại")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                TextField(
                    value = email ?: "", colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    onValueChange = {  },
                    placeholder = { Text("Email", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
            Button(
                onClick = {


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 5.dp, end = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray, // Màu nền của nút


                ),
                shape = RoundedCornerShape(10.dp) // Hình dạng của nút
            ) {
                Text(text = "Lưu", color = Color.White)
            }
        }
    }
}


enum class Gender {
    Male,
    Female
}

@Composable
fun GenderDropdown(onClick: (String) -> Unit, modifier: Modifier = Modifier, defaultGender: String? = null) {
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf(defaultGender ?: "Giới tính") }
    val genderOptions = listOf("Nam", "Nữ")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(12.dp))
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedGender,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Gender",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    onClick = {
                        selectedGender = gender
                        expanded = false
                        onClick(gender)
                    },
                    text = {
                        Text(text = gender)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun DatePickerDropdown(
    modifier: Modifier = Modifier,
    defaultDate: String? = null,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Định dạng ngày mặc định
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val initialDate = defaultDate?.let { dateFormat.parse(it) } ?: Date()

    calendar.time = initialDate

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDate by remember { mutableStateOf(defaultDate ?: "Ngày sinh") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onClick(selectedDate)
        },
        year, month, day
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(12.dp))
                .clickable { datePickerDialog.show() }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedDate,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_list_alt_24),
                    contentDescription = "Chọn ngày",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun UpdateActivityPre() {
    val context = LocalContext.current
    UpdateActivity()
}
