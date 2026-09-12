package com.example.song_list_page.core.network

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sin

// MD5摘要算法
internal object Md5 {
  private val S = intArrayOf(
    7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
    5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
    4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
    6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
  )

  private val K: IntArray = IntArray(64) { i ->
    (floor(abs(sin((i + 1).toDouble())) * 4294967296.0)).toInt()
  }

  fun digest(input: String): String = digest(input.encodeToByteArray())

  fun digest(message: ByteArray): String {
    var a0: Int = 0x67452301
    var b0: Int = 0xefcdab89.toInt()
    var c0: Int = 0x98badcfe.toInt()
    var d0: Int = 0x10325476

    // 填充：先补 0x80，再补 0 直到长度 ≡ 56 (mod 64)
    val msgLenBits = (message.size.toLong() and 0xFFFFFFFFFFFFL) * 8L
    val padded = message.toMutableList().apply { add(0x80.toByte()) }
    while (padded.size % 64 != 56) {
      padded.add(0x00.toByte())
    }
    // 追加原始长度（64-bit 小端）
    var len = msgLenBits
    repeat(8) {
      padded.add((len and 0xFF).toByte())
      len = len ushr 8
    }
    val bytes = padded.toByteArray()

    val M = IntArray(16)
    var i = 0
    while (i < bytes.size) {
      for (j in 0..15) {
        val p = i + j * 4
        M[j] = (bytes[p].toInt() and 0xFF) or
          ((bytes[p + 1].toInt() and 0xFF) shl 8) or
          ((bytes[p + 2].toInt() and 0xFF) shl 16) or
          ((bytes[p + 3].toInt() and 0xFF) shl 24)
      }
      var A = a0
      var B = b0
      var C = c0
      var D = d0
      for (k in 0..63) {
        val F: Int
        val g: Int
        when {
          k <= 15 -> {
            F = (B and C) or (B.inv() and D)
            g = k
          }

          k <= 31 -> {
            F = (D and B) or (D.inv() and C)
            g = (5 * k + 1) % 16
          }

          k <= 47 -> {
            F = B xor C xor D
            g = (3 * k + 5) % 16
          }

          else -> {
            F = C xor (B or D.inv())
            g = (7 * k) % 16
          }
        }
        val sum = (F.toLong() and 0xFFFFFFFFL) +
          (A.toLong() and 0xFFFFFFFFL) +
          (K[k].toLong() and 0xFFFFFFFFL) +
          (M[g].toLong() and 0xFFFFFFFFL)
        val rotated = sum.toInt().rotateLeft(S[k])
        val newB = B + rotated
        A = D
        D = C
        C = B
        B = newB
      }
      a0 = a0 + A
      b0 = b0 + B
      c0 = c0 + C
      d0 = d0 + D
      i += 64
    }
    return toHex(a0, b0, c0, d0)
  }

  private fun toHex(a: Int, b: Int, c: Int, d: Int): String {
    val hexChars = "0123456789abcdef"
    val sb = StringBuilder(32)
    for (v in intArrayOf(a, b, c, d)) {
      for (shift in 0..24 step 8) {
        val byte = (v ushr shift) and 0xFF
        sb.append(hexChars[byte ushr 4])
        sb.append(hexChars[byte and 0x0F])
      }
    }
    return sb.toString()
  }
}