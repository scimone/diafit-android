package scimone.diafit.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val colorScheme = darkColorScheme(
    primary = PurpleGrey80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Black,
    surface = Black,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onTertiary = DarkGray,
    onBackground = TextWhite,
    onSurface = DarkGray,
)


@Composable
fun DiafitTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}