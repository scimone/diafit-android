package scimone.diafit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import scimone.diafit.db.CGMEntity
import scimone.diafit.ui.theme.DiafitTheme
import scimone.diafit.viewmodel.CGMViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CGMViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DiafitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Greeting("Anna")
                        val latestCGMValue by viewModel.latestCGMValue.observeAsState(null)
                        latestCGMValue?.let {
                            NewestCGMValue(it.value.toString())
                        }
                    }
                }
            }
        }

        val mockCGMValue = CGMEntity(System.currentTimeMillis(), 0)
        viewModel.insertCGMValue(mockCGMValue)
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
