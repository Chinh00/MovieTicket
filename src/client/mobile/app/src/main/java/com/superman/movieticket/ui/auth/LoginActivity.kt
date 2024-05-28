import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.superman.movieticket.core.view.BaseActivity
import com.superman.movieticket.core.view.BaseScreen
import com.superman.movieticket.ui.auth.control.LoginActivityModel
import com.superman.movieticket.ui.auth.control.LoginActivityModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class LoginActivity : BaseActivity<LoginActivityModelImpl>() {
    override fun getViewModel(): Class<LoginActivityModelImpl> = LoginActivityModelImpl::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }

}


@Composable
fun HomeScreen() {
    var username = remember {
        mutableStateOf("")
    }
    BaseScreen {
        TextField(value = username.value, onValueChange = {
              username.value = it
        }, modifier = Modifier.apply {

        })
    }
}


