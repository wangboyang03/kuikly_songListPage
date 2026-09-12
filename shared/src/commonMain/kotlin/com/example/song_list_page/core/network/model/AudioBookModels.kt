package com.example.song_list_page.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 听书专辑音频列表响应模型
 */

@Serializable data class AudioBookAudiosResponse(
  @SerialName("status") val status: Int = 0,
  @SerialName("errcode") val errcode: Int = 0,
  @SerialName("error") val error: String = "",
  @SerialName("errmsg") val errmsg: String = "",
  @SerialName("total") val total: Int = 0,
  @SerialName("data") val audios: List<AudioBookAudio> = emptyList(),
  @SerialName("extra") val extra: AudioBookExtra? = null
) {
  /** 业务成功判断 */
  val isSuccess: Boolean get() = status == 1
}

@Serializable data class AudioBookAudio(
  @SerialName("album_audio_id") val albumAudioId: Long = 0,
  @SerialName("album_id") val albumId: Long = 0,
  @SerialName("album_name") val albumName: String = "",
  @SerialName("audio_id") val audioId: Long = 0,
  @SerialName("audio_name") val audioName: String = "",
  @SerialName("author_name") val authorName: String = "",
  @SerialName("hash") val hash: String = "",
  @SerialName("filesize") val filesize: Long = 0,
  @SerialName("timelength") val timelength: Long = 0,
  @SerialName("sort") val sort: Int = 0,
  @SerialName("play_times") val playTimes: Long = 0,
  @SerialName("pay_type") val payType: Int = 0,
  @SerialName("privilege") val privilege: Int = 0,
  @SerialName("special_tag") val specialTag: Int = 0,
  @SerialName("trans_param") val transParam: AudioTransParam? = null
)

@Serializable data class AudioTransParam(
  @SerialName("union_cover") val unionCover: String = "",
  @SerialName("special_tag") val specialTag: Int = 0,
  @SerialName("is_super_vip") val isSuperVip: Int = 0
)

@Serializable data class AudioBookExtra(
  @SerialName("reverse") val reverse: Int = 0,
  @SerialName("resp") val resp: AudioBookResp? = null
)

@Serializable data class AudioBookResp(
  @SerialName("base") val base: RespBase? = null,
  @SerialName("extra") val extra: RespExtra? = null,
  @SerialName("pay_info") val payInfo: PayInfo? = null,
  @SerialName("intro") val intro: String = "",
  @SerialName("full_intro") val fullIntro: String = "",
  @SerialName("play_times") val playTimes: Long = 0
)

@Serializable data class RespBase(
  @SerialName("album_id") val albumId: Long = 0,
  @SerialName("album_name") val albumName: String = "",
  @SerialName("category") val category: Int = 0,
  @SerialName("cover") val cover: String = "",
  @SerialName("language") val language: String = "",
  @SerialName("is_publish") val isPublish: Int = 0,
  @SerialName("author_name") val authorName: String = "",
  @SerialName("publish_date") val publishDate: String = ""
)

@Serializable data class RespExtra(
  @SerialName("special_tag") val specialTag: Int = 0,
  @SerialName("publish_company") val publishCompany: String = "",
  @SerialName("is_finish") val isFinish: Int = 0
)

@Serializable data class PayInfo(
  @SerialName("pay_privilege") val payPrivilege: Int = 0,
  @SerialName("pay_res_type") val payResType: String = "",
  @SerialName("special_tag") val specialTag: Int = 0,
  @SerialName("is_pay") val isPay: Int = 0,
  @SerialName("listen_coupon") val listenCoupon: Int = 0,
  @SerialName("pay_price") val payPrice: Int = 0,
  @SerialName("pay_hash") val payHash: String = ""
)