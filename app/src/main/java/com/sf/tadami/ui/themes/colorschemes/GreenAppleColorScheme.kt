package com.sf.tadami.ui.themes.colorschemes

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Colors for Green Apple theme
 * Original color scheme by CarlosEsco, Jays2Kings and CrepeTF
 * M3 colors generated by Material Theme Builder (https://goo.gle/material-theme-builder-web)
 *
 * Key colors:
 * Primary #188140
 * Secondary #188140
 * Tertiary #D33131
 * Neutral #5D5F5B
 */
internal object GreenAppleColorScheme : BaseColorScheme() {

    override val darkScheme = darkColorScheme(
        primary = Color(0xFF7ADB8F),
        onPrimary = Color(0xFF003915),
        primaryContainer = Color(0xFF005322),
        onPrimaryContainer = Color(0xFF96F8A9),
        inversePrimary = Color(0xFF006D2F),
        secondary = Color(0xFF7ADB8F),
        onSecondary = Color(0xFF003915),
        secondaryContainer = Color(0xFF005322),
        onSecondaryContainer = Color(0xFF96F8A9),
        tertiary = Color(0xFFFFB3AA),
        onTertiary = Color(0xFF680006),
        tertiaryContainer = Color(0xFF93000D),
        onTertiaryContainer = Color(0xFFFFDAD5),
        background = Color(0xFF1A1C19),
        onBackground = Color(0xFFE1E3DD),
        surface = Color(0xFF1A1C19),
        onSurface = Color(0xFFE1E3DD),
        surfaceVariant = Color(0xFF414941),
        onSurfaceVariant = Color(0xFFC1C8BE),
        surfaceTint = Color(0xFF7ADB8F),
        inverseSurface = Color(0xFFE1E3DD),
        inverseOnSurface = Color(0xFF1A1C19),
        outline = Color(0xFF8B9389),
    )

    override val lightScheme = lightColorScheme(
        primary = Color(0xFF006D2F),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFF96F8A9),
        onPrimaryContainer = Color(0xFF002109),
        inversePrimary = Color(0xFF7ADB8F),
        secondary = Color(0xFF006D2F),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFF96F8A9),
        onSecondaryContainer = Color(0xFF002109),
        tertiary = Color(0xFFB91D22),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFFFDAD5),
        onTertiaryContainer = Color(0xFF410003),
        background = Color(0xFFFBFDF7),
        onBackground = Color(0xFF1A1C19),
        surface = Color(0xFFFBFDF7),
        onSurface = Color(0xFF1A1C19),
        surfaceVariant = Color(0xFFDDE5DA),
        onSurfaceVariant = Color(0xFF414941),
        surfaceTint = Color(0xFF006D2F),
        inverseSurface = Color(0xFF2F312E),
        inverseOnSurface = Color(0xFFF0F2EC),
        outline = Color(0xFF717970),
    )
}
