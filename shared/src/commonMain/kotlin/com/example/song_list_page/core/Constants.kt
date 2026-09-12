package com.example.song_list_page.core

internal object Constants {
  const val BASE_URL = "https://openapi.kugou.com" // 基地址
  const val ALBUM_AUDIOS = "/v1/union_long/album_audios" // 长音频歌曲列表接口

  const val APP_ID = "3413"       // Constants.appId
  const val CLIENT_VERSION = "20292"  // Utils.getVersionCode()

  var APP_KEY = "" // 服务端签名密钥 宿主安全下发后赋值
  var INJECT_COMMON_PARAMS_BY_HOST = false
}

internal object DeviceProvider {
  var dfid: String = ""
  var mid: String = ""
  var uuid: String = ""
  var userId: String = ""
  var token: String = ""
  fun isLogin(): Boolean = userId.isNotBlank() && token.isNotBlank()
}