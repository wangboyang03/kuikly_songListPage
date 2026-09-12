package com.example.song_list_page.core.network

import com.tencent.kuikly.core.module.NetworkModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.core.pager.Pager
import kotlinx.serialization.json.Json
import kotlin.coroutines.suspendCoroutine

// 基于Kuikly的NetworkModule实现的接口封装
// 坑点 鸿蒙不支持KMP框架的Ktor 因此用官方的NetworkKMM
// 坑点 Kuikly网络层要求必须使用JSONObject 因此不能直接写serialization
// 坑点 requestGet无法指定请求头 但是接口要求application/json 因此使用httpRequest 通过isPost控制请求类型
class ApiClient(@PublishedApi internal val pager: Pager) {
  val module by lazy(LazyThreadSafetyMode.NONE) {
    pager.acquireModule<NetworkModule>(NetworkModule.MODULE_NAME)
  }
  // Kotlin在运行期会将泛型擦除全部变成上界 函数不知道T的具体类型无法编译
  // reified是具象化泛型 能够让泛型在调用处保留 但是reified必须搭配inline
  // 因为非inline函数会生成独立的方法 签名里泛型依然会被擦除

  /** GET请求 params是Query参数 */
  suspend inline fun <reified T> get(url: String, params: Map<String, Any>? = null): T =
    request(isPost = false, url = url, body = params)

  /** POST请求 body作为JSON请求体 需要序列化对象请用postSerializable */
  suspend inline fun <reified T> post(url: String, body: Map<String, Any>? = null): T =
    request(isPost = true, url = url, body = body)

  /** PUT请求 坑点 原生无PUT方法 通过POST构造 */
  suspend inline fun <reified T> put(url: String, body: Map<String, Any>? = null): T =
    request(isPost = true, url = url, body = body, method = "PUT")

  /** DELETE请求 */
  suspend inline fun <reified T> delete(url: String, body: Map<String, Any>? = null): T =
    request(isPost = true, url = url, body = body, method = "DELETE")


  suspend inline fun <reified T> request(isPost: Boolean, url: String, body: Map<String, Any>?, method: String? = null): T = suspendCoroutine { cont ->
    val param = JSONObject().apply {
      body?.forEach { (key, value) ->
        put(key, value)
      }
    }
    val headers = JSONObject().apply {
      // 指定请求头 按需重写Http方法
      put("Content-Type", "application/json")
      if (method != null) put("X-HTTP-Method-Override", method)
    }
    module.httpRequest(url, isPost, param, headers, null, 30) { data, success, errMsg, resp ->
      if (success) {
        cont.resumeWith(runCatching { json.decodeFromString<T>(data.toString()) })
      } else {
        cont.resumeWith(Result.failure(ApiException(resp.statusCode ?: -1, errMsg)))
      }
    }
  }

  companion object {
    val json: Json = Json {
      ignoreUnknownKeys = true   // 忽略接口多余字段
      isLenient = true
      coerceInputValues = true  // 缺字段时用默认值，不抛异常
      encodeDefaults = true
    }
  }
}

/** 
 * 网络错误
 **/
class ApiException(val code: Int, message: String) : Exception(message)