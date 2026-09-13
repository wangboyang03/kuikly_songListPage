package com.example.song_list_page.core.network

import com.example.song_list_page.core.Constants
import com.example.song_list_page.core.DeviceProvider
import com.tencent.kuikly.core.pager.Pager
import com.example.song_list_page.core.network.model.AudioBookAudiosResponse
import kotlinx.datetime.Clock

object LBookAlbumAudioApi {
  /**
   * 请求参数
   * @param albumId    听书专辑 ID
   * @param page       页码 默认从1开始
   * @param pageSize   每页数量 默认50
   * @param newerFirst 从新到旧(reverse=1) 从旧到新(reverse=0)
   */
  data class AlbumAudiosRequest(
    val albumId: Long,
    val page: Int = 1,
    val pageSize: Int = 50,
    val newerFirst: Boolean = false
  )

  suspend fun get(pager: Pager, request: AlbumAudiosRequest): AudioBookAudiosResponse {
    val client = ApiClient(pager)
    return client.get("${Constants.BASE_URL}${Constants.ALBUM_AUDIOS}", buildRequestParams(request))
  }

  // 拼接参数
  private fun buildRequestParams(request: AlbumAudiosRequest): Map<String, Any> {
    val map = LinkedHashMap<String, Any>()
    map["album_id"] = request.albumId
    map["page"] = request.page
    map["pagesize"] = request.pageSize
    map["reverse"] = if (request.newerFirst) 1 else 0
    // need_extra_album_info
    if (request.page == 1) {
      map["need_extra_album_info"] = 1
    }

    if (Constants.INJECT_COMMON_PARAMS_BY_HOST) {
      // 公共参数与签名
      return map
    }

    // 业务层补充参数
    map["appid"] = Constants.APP_ID
    map["clientver"] = Constants.CLIENT_VERSION
    map["clienttime"] = Clock.System.now().epochSeconds.toString()
    if (DeviceProvider.dfid.isNotBlank()) map["dfid"] = DeviceProvider.dfid
    if (DeviceProvider.mid.isNotBlank()) map["mid"] = DeviceProvider.mid
    if (DeviceProvider.uuid.isNotBlank()) map["uuid"] = DeviceProvider.uuid
    if (DeviceProvider.isLogin()) {
      map["userid"] = DeviceProvider.userId
      map["token"] = DeviceProvider.token
    }

    // 计算签名 !signature
    val signMap = LinkedHashMap<String, String>()
    map.forEach { (k, v) -> signMap[k] = v.toString() }
    map["signature"] = Signer.sign(signMap, Constants.APP_KEY)
    return map
  }
}