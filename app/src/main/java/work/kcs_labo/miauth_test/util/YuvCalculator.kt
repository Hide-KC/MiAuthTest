package work.kcs_labo.miauth_test.util

import android.graphics.Color
import androidx.annotation.ColorInt

object YuvCalculator {
  @ColorInt
  fun getForegroundColor(
    @ColorInt color: Int,
    thresh: Int = 180
  ): Int {
    val fixedThresh = thresh.coerceIn(0..255)

    return if (colorToYuv(color) > fixedThresh) {
      Color.BLACK
    } else {
      Color.WHITE
    }
  }

  private fun colorToYuv(color: Int): Double {
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    return 0.299 * red + 0.587 * green + 0.144 * blue
  }

  fun darken(@ColorInt base: Int, amount: Int = 12): Int {
    val fixedAmount = amount.coerceIn(0..100)

    val hsv = FloatArray(3)
    Color.colorToHSV(base, hsv)

    val hsl = hsv2Hsl(hsv)

    hsl[2] = (hsl[2] - fixedAmount / 100f).coerceAtLeast(0f)

    val nHsv = hsl2Hsv(hsl)
    return Color.HSVToColor(nHsv)
  }

  fun lighten(@ColorInt base: Int, amount: Int): Int {
    val fixedAmount = amount.coerceIn(0..100)

    val hsv = FloatArray(3)
    Color.colorToHSV(base, hsv)

    val hsl = hsv2Hsl(hsv)

    hsl[2] = (hsl[2] + fixedAmount / 100f).coerceAtMost(1f)

    val nHsv = hsl2Hsv(hsl)
    return Color.HSVToColor(nHsv)
  }

  private fun hsv2Hsl(hsv: FloatArray): FloatArray {
    val h = hsv[0]
    val s = hsv[1]
    val v = hsv[2]

    val nH = (2f - s) * v
    val nS = (s * v / (if (nH < 1f) {
      nH
    } else {
      2f - nH
    })).coerceAtMost(1f)

    return floatArrayOf(
      h,
      nS,
      nH / 2f
    )
  }

  private fun hsl2Hsv(hsl: FloatArray): FloatArray {
    val h = hsl[0]
    val l = hsl[2]
    val s = hsl[1] * (if (l < 0.5) {
      l
    } else {
      1 - l
    })

    return floatArrayOf(
      h,
      2f * s / (l + s),
      l + s
    )
  }
}