package scimone.diafit.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.component.LineComponent
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.presentation.model.CGMChartData
import scimone.diafit.features.home.presentation.components.CustomAxisItemPlacer

@Composable
fun ChartComponentCGM(allCGMFromToday: List<CGMChartData>) {
    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(allCGMFromToday) {
        if (allCGMFromToday.isNotEmpty()) {
            modelProducer.tryRunTransaction {
                lineSeries {
                    val xValues = allCGMFromToday.map { it.timeFloat }
                    val yValues = allCGMFromToday.map { it.value }
//                    println("testest allCGMFromToday: $allCGMFromToday")
//                    println("testest xValues: $xValues")
//                    println("testestyValues: $yValues")
                    series(x = xValues, y = yValues)
                }
            }
        }
    }
    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                axisValueOverrider = AxisValueOverrider.fixed(minY = 0f, maxY = 250f),
                verticalAxisPosition = AxisPosition.Vertical.Start
            ),
            startAxis = rememberStartAxis(
                guideline = LineComponent(
                    color = MaterialTheme.colorScheme.onSurface.toArgb(),
                    thicknessDp = .5f,
                ),
                itemPlacer = remember { CustomAxisItemPlacer() },
            ),
            bottomAxis = rememberBottomAxis(
                guideline = LineComponent(
                    color = MaterialTheme.colorScheme.onSurface.toArgb(),
                    thicknessDp = .5f,
                ),
            ),
        ),
        modelProducer,
        zoomState = rememberVicoZoomState(zoomEnabled = true, initialZoom = Zoom.Companion.Content),
        scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.Companion.End),
        getXStep = { 1f }

        )
}