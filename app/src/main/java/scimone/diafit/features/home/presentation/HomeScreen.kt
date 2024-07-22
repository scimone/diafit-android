package scimone.diafit.features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import scimone.diafit.components.ChartComponentCGM
import scimone.diafit.features.home.presentation.components.ComponentRotatingArrowIcon
import scimone.diafit.ui.theme.aboveRange
import scimone.diafit.ui.theme.belowRange
import scimone.diafit.ui.theme.inRange

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()


    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Text(
                text = "${state.latestCGM?.value}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = when {
                    (state.latestCGM?.value ?: 0) <= 70 -> belowRange
                    (state.latestCGM?.value ?: 0) >= 180 -> aboveRange
                    else -> inRange
                },
                textDecoration = if (state.staleCGM) TextDecoration.LineThrough else TextDecoration.None

            )
            ComponentRotatingArrowIcon(inputValue = state.rateAvg)
        }
        Text(text = "${state.timeSinceLastCGM} ago")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            ChartComponentCGM(state.allCGMSince24h)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Today's Bolus:")
                state.allBolus24h.forEach { bolus ->
                    Text(text = "${bolus.timestampString} ${bolus.amount} U")
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Today's Carbs:")
                state.allCarbs24h.forEach { carbs ->
                    Text(text = "${carbs.timestampString} ${carbs.amount} g")
                }
            }
        }
    }
}
