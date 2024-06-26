package com.superman.movieticket.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.entities.User
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.auth.control.PhoneOtpActivityViewModel
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.profile.control.UpdateActivityViewModel
import com.superman.movieticket.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class UpdateActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val updateActivityViewModel: UpdateActivityViewModel by viewModels()

            val user = updateActivityViewModel.userinfo.collectAsState().value
            MyAppTheme {
                BaseScreen(content = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        if (user != null) {
                            UpdateScreen(user)

                        }
                    }
                }, title = "Thông tin người dùng", onNavigateUp = { finish() })
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateScreen(
    user: User?
) {
    val updateActivityViewModel: UpdateActivityViewModel = hiltViewModel()

    val apiState = updateActivityViewModel.apiLoading.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    if (apiState != ApiState.NONE) {
        DisposableEffect(apiState) {
            showDialog = true
            scope.launch {
                delay(1000)
                showDialog = false
            }
            onDispose {
                // Clean up when the effect leaves the composition
                showDialog = false
            }
        }
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
//            DialogProperties(
//                dismissOnBackPress = false,
//                dismissOnClickOutside = false
//            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                when (apiState) {
                    ApiState.LOADING -> {
                        CircularProgressIndicator()
                    }

                    ApiState.SUCCESS -> {
                        Icon(
                            Icons.Default.CheckCircleOutline,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }

                    ApiState.FAIL -> {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }

                    else -> {
                        // Handle other cases if needed
                    }
                }

            }
        }
    }
    fun uploadImageToFirebase(
        fileUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference
        val fileReference: StorageReference =
            storageReference.child("images/${fileUri.lastPathSegment}")

        fileReference.putFile(fileUri)

            .addOnSuccessListener { taskSnapshot ->
                if(taskSnapshot.task.isInProgress){
                        updateActivityViewModel._apiLoading.value=ApiState.LOADING
                }

                if(taskSnapshot.task.isSuccessful){
                    updateActivityViewModel._apiLoading.value=ApiState.SUCCESS

                    fileReference.downloadUrl.addOnSuccessListener { uri ->
                        onSuccess(uri.toString())
                    }
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
                updateActivityViewModel._apiLoading.value=ApiState.FAIL

            }
        updateActivityViewModel._apiLoading.value=ApiState.NONE
    }

    var avatar by remember { mutableStateOf(user?.avatar ?: "") }
    var fullname by remember { mutableStateOf(user?.fullName ?: "") }
    var birthday by remember {
        mutableStateOf(
            if (user?.birthday != null || user?.birthday != "") DatetimeHelper.ConvertISODatetimeToLocalDatetime(
                user!!.birthday,
                "dd/MM/yyyy"
            ) else ""
        )
    }
    var uploadUrl by remember { mutableStateOf<String?>(avatar) }

    var gen by remember { mutableStateOf(user!!.userGender ?: 1) }
    val context = LocalContext.current
// Image picker launcher
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->


        uploadImageToFirebase(fileUri = Uri.parse(uri.toString()),
            onSuccess = {
                uploadUrl = it
                Log.d("uploadUrl", "UpdateScreen: $it")
            },
            onFailure = { uploadUrl = "" })
    }
    // Request permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Open image picker
            launcher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }



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
                    painter = if (imageUri.value.toString() == "" || imageUri.value == null) {
                        rememberAsyncImagePainter(
                            model = uploadUrl,
                            error = painterResource(id = R.drawable.avatar)
                        )
                    } else {
                        rememberImagePainter(data = imageUri.value)
                    }, // Thay đổi ID ảnh tương ứng
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            updateActivityViewModel._apiLoading.value=ApiState.LOADING

                            if (ContextCompat.checkSelfPermission(

                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                launcher.launch("image/*")
                            } else {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        },
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
                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    value = fullname,
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done), keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    onValueChange = { fullname = it },
                    label = { Text(text = "Họ tên") },
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
                val mYear: Int
                val mMonth: Int
                val mDay: Int
                val mCalendar = Calendar.getInstance()

                // Fetching current year, month and day
                mYear = mCalendar.get(Calendar.YEAR)
                mMonth = mCalendar.get(Calendar.MONTH)
                mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
                mCalendar.time = Date()

                val mDatePickerDialog = DatePickerDialog(
                    context,
                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                        birthday = String.format("%02d/%02d/%d", mDayOfMonth, mMonth + 1, mYear)
                    }, mYear, mMonth, mDay
                )
                Log.d("birthday", "UpdateScreen: $birthday")
                TextField(
                    value = birthday, colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    readOnly = true,
                    onValueChange = { },
                    label = { Text(text = "Ngày sinh") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                    trailingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable {
                                mDatePickerDialog.show()
                            })
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Giới tính")
                    Switch(
                        checked = if (gen == 1) true else false,
                        onCheckedChange = {
                            if (it) {
                                gen = 1
                                Log.d("gen", "UpdateScreen: $gen")
                            } else {
                                gen = 0

                            }

                        }, colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = Color.Green,
                            uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                            uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        thumbContent = if (gen == 1) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Man,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        } else {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Woman,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        }
                    )

                }

            }
            // thêm thì nó báo lỗi date đấy ko biết bị sao
            val updateActivityViewModel: UpdateActivityViewModel = hiltViewModel()
            val scope = rememberCoroutineScope()
            Spacer(modifier = Modifier.height(80.dp))
            Button(
                onClick = {

                    scope.launch {

                        updateActivityViewModel.HandleUpdateUseInfo(
                            fullName = fullname,
                            birthday = birthday, userGender = gen, avatar = uploadUrl ?: ""
                        )
                    }
                    // vcd gioi tinh nó bị lỗi ngày sinh
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
fun GenderDropdown(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    defaultGender: String? = null
) {
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
