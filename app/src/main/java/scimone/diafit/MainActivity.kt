package scimone.diafit

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

// MainActivity extends ComponentActivity and implements OnUpdateListener
class MainActivity : ComponentActivity(), CGMReceiver.OnUpdateListener {

    // Mutable state for holding the CGM value
    private val cgmValue = mutableStateOf("")

    // Instance of CGMReceiver
    private var cgmReceiver: CGMReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize CGMReceiver and set MainActivity as listener
        cgmReceiver = CGMReceiver()
        cgmReceiver?.setOnUpdateListener(this)

        // Register CGMReceiver to listen for broadcasts with intent filter "glucodata.Minute"
        registerReceiver(cgmReceiver, IntentFilter("glucodata.Minute"))
        Log.i("MainActivity", "Receiver registered")

        // Set the content of the activity using Jetpack Compose
        setContent {
            DiafitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        // Display a greeting
                        Greeting("Anna")

                        // Display the latest CGM value
                        NewestCGMValue(cgmValue.value)
                    }
                }
            }
        }
    }

    // Callback method called by CGMReceiver when a new glucose value is received
    override fun onUpdate(newCgmValue: Float) {
        Log.d("MainActivity", "New glucose value received: $newCgmValue")

        // Update the UI with the new CGM value
        cgmValue.value = newCgmValue.toString()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the receiver when the activity is destroyed to prevent leaks
        unregisterReceiver(cgmReceiver)
        Log.i("MainActivity", "Receiver unregistered")
    }
}

// Composable function to display a greeting
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Composable function to display the newest CGM value
@Composable
fun NewestCGMValue(cgmValue: String, modifier: Modifier = Modifier) {
    Text(
        text = "Your latest CGM value is $cgmValue",
        modifier = modifier
    )
}

// Preview function for Greeting composable
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiafitTheme {
        Greeting("Android")
    }
}
