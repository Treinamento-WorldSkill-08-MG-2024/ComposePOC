package worldskills.mg.spinola

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import worldskills.mg.spinola.ui.theme.ComposePOCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ComposePOCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash") {
                            Greeting(
                                name = "Android",
                                navController,
                            )
                        }
                        composable("login") {
                            Login(navCt = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, navCt: NavHostController, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )

    LaunchedEffect(true) {
        delay(3_000)
        navCt.navigate("login")
    }
}

@Composable
fun Login(navCt: NavHostController) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            imageUri = uri
         }
    LaunchedEffect(key1 = imageUri) {
        val request = ImageRequest.Builder(context)
            .data(imageUri)
            .build()
    }
    Column {
        Text("LoginScreen")
        Button(onClick = { photoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
            Text(text = "Escolha foto")
        }
        AsyncImage(model = imageUri, contentDescription = "selected image")
    }
    Button(onClick = { navCt.popBackStack() }) {
        Text(text = "Back")
    }
}