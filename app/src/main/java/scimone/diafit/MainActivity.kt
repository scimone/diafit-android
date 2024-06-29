package scimone.diafit

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import scimone.diafit.ui.theme.DiafitTheme

class MainActivity : ComponentActivity(), CGMReceiver.OnUpdateListener {
    private val cgmValue = mutableStateOf("")

    private var cgmReceiver: CGMReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cgmReceiver = CGMReceiver()
        cgmReceiver?.setOnUpdateListener(this)
        registerReceiver(cgmReceiver, IntentFilter("glucodata.Minute"), RECEIVER_EXPORTED)
        Log.i("MainActivity", "Receiver registered")

        setContent {
            DiafitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Greeting("Anna")
                        NewestCGMValue(cgmValue.value)
                    }
                }
            }
        }
    }

    override fun onUpdate(newCgmValue: Float) {
        Log.d("MainActivity", "New glucose value received: $newCgmValue")
        cgmValue.value = newCgmValue.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(cgmReceiver)
        Log.i("MainActivity", "Receiver unregistered")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun NewestCGMValue(cgmValue: String, modifier: Modifier = Modifier) {
    Text(
        text = "Your latest CGM value is $cgmValue",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiafitTheme {
        Greeting("Android")
    }
}
