package com.kitching.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kitching.core.R

val pretendard = FontFamily(
    Font(R.font.pretendard_black, FontWeight.Black),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_thin, FontWeight.Thin),
)

val H1 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp
)

val H1_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 28.sp
)

val H2 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp
)

val H2_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp
)

val H3 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)

val H3_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp
)

val H4 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp
)

val H4_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp
)

val H5 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)

val H5_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)

/** Regular = Normal */
val Body1 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

val Body1_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)

val Body2 = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)

val Body2_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)

val Caption1_R = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

val Caption1_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)

val Caption2_R = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp
)

val Caption2_m = TextStyle(
    fontFamily = pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp
)

val customTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)