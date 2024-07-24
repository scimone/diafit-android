package scimone.diafit.components

import android.text.Layout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberPoint
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Shape
import scimone.diafit.core.presentation.model.CGMChartData
import scimone.diafit.features.home.presentation.components.CustomAxisItemPlacer
import scimone.diafit.ui.theme.TextWhite
import scimone.diafit.ui.theme.aboveRange
import scimone.diafit.ui.theme.belowRange
import java.text.DecimalFormat
import java.util.Calendar

@Composable
fun ChartComponentCGM(allCGMFromToday: List<CGMChartData>) {
    val modelProducer = remember { CartesianChartModelProducer.build() }
    val currentTime = System.currentTimeMillis()
    val oneDayAgo = currentTime - 24 * 60 * 60 * 1000 // 24 hours in milliseconds

    LaunchedEffect(allCGMFromToday) {
        if (allCGMFromToday.isNotEmpty()) {
            modelProducer.runTransaction {
                lineSeries {
                    val filteredCGMData =
                        allCGMFromToday.filter { it.timeFloat >= oneDayAgo && it.timeFloat <= currentTime }
                    val xValues = filteredCGMData.map { it.timeFloat }
                    val yValues = filteredCGMData.map { it.value }
                    series(x = xValues, y = yValues)
                }
            }
        }
    }

    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider =
                    LineCartesianLayer.LineProvider.series(
                        rememberLine(
                            pointProvider =
                                LineCartesianLayer.PointProvider.single(
                                    rememberPoint(
                                        component = ShapeComponent(
                                            shape = Shape.Pill,
                                            color = MaterialTheme.colorScheme.primary.toArgb(),
                                        ),
                                        size = 4.dp,
                                    )
                                ),
                            thickness = 0.5.dp,
                        ),
                ),
                verticalAxisPosition = AxisPosition.Vertical.Start,
                axisValueOverrider = AxisValueOverrider.fixed(
                    minX = oneDayAgo.toDouble(),
                    maxX = currentTime.toDouble(),
                )
            ),
            startAxis = rememberStartAxis(
                horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
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
                label = rememberAxisLabelComponent(
                    textAlignment = Layout.Alignment.ALIGN_CENTER,
                    textSize = 10.sp,
                ),
                valueFormatter = { value, _, _ ->
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = value.toLong()
                    }
                    val hours = calendar.get(Calendar.HOUR_OF_DAY)
                    String.format("%02d", hours)
                },
            ),
            getXStep = { 3600000.0 }, // 1 hour in milliseconds
            marker = rememberDefaultCartesianMarker(
                label = TextComponent(
                    color = MaterialTheme.colorScheme.onBackground.toArgb(),
                    textSizeSp = 10f,
                ),
                labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint,
                indicatorSize = 10.dp,
                guideline = LineComponent(
                    color = MaterialTheme.colorScheme.onBackground.toArgb(),
                    thicknessDp = .5f,
                ),
                valueFormatter = DefaultCartesianMarkerValueFormatter(decimalFormat = DecimalFormat("#.## mg/dl"), colorCode = false)
            )

        ),
        modelProducer,
        zoomState = rememberVicoZoomState(zoomEnabled = true, initialZoom = Zoom.Companion.Content),
        scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.Companion.End),
    )
}