package com.example.song_list_page.base

import com.tencent.kuikly.core.base.BaseObject
import com.tencent.kuikly.core.manager.BridgeManager
import com.tencent.kuikly.core.manager.PagerManager

internal object Utils : BaseObject() {

  fun bridgeModule(pager: String): BridgeModule {
    return PagerManager.getPager(pager).acquireModule<BridgeModule>(BridgeModule.MODULE_NAME)
  }

  fun logToNative(pagerId: String, content: String) {
    // logToNaive
    bridgeModule(pagerId).log(content)
  }

  fun currentBridgeModule(): BridgeModule {
    return PagerManager.getPager(BridgeManager.currentPageId).acquireModule<BridgeModule>(
      BridgeModule.MODULE_NAME
    )
  }

  fun logToNative(content: String) {
    bridgeModule(BridgeManager.currentPageId).log(content)
  }

  fun convertToPriceStr(price: Long): String {
    return (price / 100f).toString()
  }

  fun getSizeImage(size: Int, imageUrl: String): String {
    return imageUrl.replace("{size}", size.toString())
  }

  fun formatViewCount(count: Int): String {
    val (value, unit) = when {
      count >= 100_000_000 -> count / 100_000_000 to "亿"
      count >= 10_000 -> count / 10_000 to "万"
      else -> return count.toInt().toString()
    }
    val rounded = (value * 10).toInt() / 10f
    return "${if (rounded % 1f == 0f) rounded.toInt() else rounded}$unit"
  }

  fun getPublishTime(publishDate: String?): String {
    val parts = publishDate?.split("-") ?: return "未知"
    return if (parts.size == 3) {
      "${parts[0]}年${parts[1]}月${parts[2]}日"
    } else {
      "未知"
    }
  }
}