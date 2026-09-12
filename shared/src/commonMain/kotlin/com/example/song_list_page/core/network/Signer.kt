package com.example.song_list_page.core.network

/**
 * 请求签名 signatureV2
 */
internal object Signer {
  fun sign(params: Map<String, String>, appKey: String): String {
    val source = appKey + buildSignSource(params) + appKey
    return Md5.digest(source)
  }

  private fun buildSignSource(params: Map<String, String>): String {
    val sorted = params.entries.sortedBy { it.key }
    val sb = StringBuilder()
    for ((k, v) in sorted) {
      sb.append(k).append(v)
    }
    return sb.toString()
  }
}
