package com.kitching.core.common.util

/** hex 문자열을 받아 Color Int로 변환 */
fun hexToArgb(hex: String): Int {
    val cleanHex = hex.removePrefix("#")

    if (cleanHex.length != 6) {
        return 0xFFFFFFFF.toInt() // 기본값: 흰색
    }

    return try {
        val r = Integer.parseInt(cleanHex.substring(0, 2), 16)
        val g = Integer.parseInt(cleanHex.substring(2, 4), 16)
        val b = Integer.parseInt(cleanHex.substring(4, 6), 16)

        (0xFF000000 or ((r shl 16).toLong()) or ((g shl 8).toLong()) or b.toLong()).toInt()
    } catch (e: NumberFormatException) {
        0xFFFFFFFF.toInt()
    }
}