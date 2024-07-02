package scimone.diafit.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Column {
        state.latestCGM?.let { cgm ->
            Text(text = "${cgm.value} mg/dL, ${state.timeSinceLastCGM} seconds ago")
        }

        state.latestCGM?.let { cgm ->
            Text(text = "(Rate: ${cgm.rate} mg/dL, Trend: ${cgm.trend})")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Today's Bolus:")
        state.allBolusFromToday.forEach { bolus ->
            Text(text = "${bolus.timestampString} ${bolus.amount} U")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Today's Carbs:")
        state.allCarbsFromToday.forEach { carbs ->
            Text(text = "${carbs.timestampString} ${carbs.amount} g")
        }

    }
}
