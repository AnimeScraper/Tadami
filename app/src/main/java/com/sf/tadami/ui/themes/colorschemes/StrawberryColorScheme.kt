package com.sf.tadami.ui.themes.colorschemes

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Colors for Strawberry Daiquiri theme
 * Original color scheme by Soitora
 * M3 color scheme generated by Material Theme Builder (https://goo.gle/material-theme-builder-web)
 *
 * Key colors:
 * Primary #ED4A65
 * Secondary #ED4A65
 * Tertiary #775930
 * Neutral #655C5C
 */
internal object StrawberryColorScheme : BaseColorScheme() {

    override val darkScheme = darkColorScheme(
        primary = Color(0xFFFFB2B9),
        onPrimary = Color(0xFF67001B),
        primaryContainer = Color(0xFF91002A),
        onPrimaryContainer = Color(0xFFFFDADD),
        inversePrimary = Color(0xFFB61E40),
        secondary = Color(0xFFFFB2B9),
        onSecondary = Color(0xFF67001B),
        secondaryContainer = Color(0xFF91002A),
        onSecondaryContainer = Color(0xFFFFDADD),
        tertiary = Color(0xFFE8C08E),
        onTertiary = Color(0xFF432C06),
        tertiaryContainer = Color(0xFF5D421B),
        onTertiaryContainer = Color(0xFFFFDDB1),
        background = Color(0xFF201A1A),
        onBackground = Color(0xFFECDFDF),
        surface = Color(0xFF201A1A),
        onSurface = Color(0xFFECDFDF),
        surfaceVariant = Color(0xFF534344),
        onSurfaceVariant = Color(0xFFD7C1C2),
        surfaceTint = Color(0xFFFFB2B9),
        inverseSurface = Color(0xFFECDFDF),
        inverseOnSurface = Color(0xFF201A1A),
        outline = Color(0xFFA08C8D),
    )

    override val lightScheme = lightColorScheme(
        primary = Color(0xFFB61E40),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFFFDADD),
        onPrimaryContainer = Color(0xFF40000D),
        inversePrimary = Color(0xFFFFB2B9),
        secondary = Color(0xFFB61E40),
        onSecondary = Color(0xFFFFFFFF),
        secondaryContainer = Color(0xFFFFDADD),
        onSecondaryContainer = Color(0xFF40000D),
        tertiary = Color(0xFF775930),
        onTertiary = Color(0xFFFFFFFF),
        tertiaryContainer = Color(0xFFFFDDB1),
        onTertiaryContainer = Color(0xFF2A1800),
        background = Color(0xFFFCFCFC),
        onBackground = Color(0xFF201A1A),
        surface = Color(0xFFFCFCFC),
        onSurface = Color(0xFF201A1A),
        surfaceVariant = Color(0xFFF4DDDD),
        onSurfaceVariant = Color(0xFF534344),
        surfaceTint = Color(0xFFB61E40),
        inverseSurface = Color(0xFF362F2F),
        inverseOnSurface = Color(0xFFFBEDED),
        outline = Color(0xFF857374),
    )
}