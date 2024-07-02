package scimone.diafit.features.home.presentation.components

import com.patrykandpatrick.vico.core.cartesian.CartesianDrawContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasureContext
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis

class CustomAxisItemPlacer : AxisItemPlacer.Vertical {
    override fun getShiftTopLines(context: CartesianDrawContext): Boolean = true

    override fun getLabelValues(
        context: CartesianDrawContext,
        axisHeight: Float,
        maxLabelHeight: Float,
        position: AxisPosition.Vertical
    ): List<Float> = listOf(70f, 180f)

    override fun getWidthMeasurementLabelValues(
        context: CartesianMeasureContext,
        axisHeight: Float,
        maxLabelHeight: Float,
        position: AxisPosition.Vertical
    ): List<Float> = listOf(70f, 180f)

    override fun getHeightMeasurementLabelValues(
        context: CartesianMeasureContext,
        position: AxisPosition.Vertical
    ): List<Float> = listOf(70f, 180f)

    override fun getLineValues(
        context: CartesianDrawContext,
        axisHeight: Float,
        maxLabelHeight: Float,
        position: AxisPosition.Vertical
    ): List<Float>? = listOf(70f, 180f)

    override fun getTopVerticalAxisInset(
        context: CartesianMeasureContext,
        verticalLabelPosition: VerticalAxis.VerticalLabelPosition,
        maxLabelHeight: Float,
        maxLineThickness: Float
    ): Float = 0f

    override fun getBottomVerticalAxisInset(
        context: CartesianMeasureContext,
        verticalLabelPosition: VerticalAxis.VerticalLabelPosition,
        maxLabelHeight: Float,
        maxLineThickness: Float
    ): Float = 0f
}