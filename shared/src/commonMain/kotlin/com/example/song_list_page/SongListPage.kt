package com.example.song_list_page

import com.example.song_list_page.base.BasePager
import com.example.song_list_page.base.Utils
import com.example.song_list_page.base.Utils.formatViewCount
import com.example.song_list_page.core.network.ApiException
import com.example.song_list_page.core.network.LBookAlbumAudioApi
import com.example.song_list_page.core.network.model.AudioBookAudiosResponse
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.Color
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.attr.ImageUri
import com.tencent.kuikly.core.coroutines.launch
import com.tencent.kuikly.core.layout.FlexAlign
import com.tencent.kuikly.core.layout.FlexDirection
import com.tencent.kuikly.core.layout.FlexJustifyContent
import com.tencent.kuikly.core.pager.PageData
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View

@Page("router") internal class SongListPage: BasePager() {
  override fun created() {
    super.created()
    loadAlbumAudios()
  }

  override fun pageWillDestroy() {
    super.pageWillDestroy()
  }

  // 请求听书专辑音频列表
  private fun loadAlbumAudios() {
    val request = LBookAlbumAudioApi.AlbumAudiosRequest(albumId = 138195288L, page = 1)
    lifecycleScope.launch {
      try {
        // Utils.logToNative("开始请求听书列表, thread=${Thread.currentThread().name}")
        val resp: AudioBookAudiosResponse = LBookAlbumAudioApi.get(this@SongListPage, request)
        Utils.logToNative("听书列表: status=${resp.status}, total=${resp.total}, size=${resp.audios.size}")
        resp.audios.firstOrNull()?.let { audio ->
          Utils.logToNative("首条音频: ${audio.audioName} / ${audio.authorName} / hash=${audio.hash}")
        }
      } catch (e: ApiException) {
        Utils.logToNative("听书列表请求失败: code=${e.code}, msg=${e.message}")
      } catch (e: Exception) {
        Utils.logToNative("听书列表解析失败: ${e.message}")
      }
    }
  }

  override fun body(): ViewBuilder {
    val context = this
    val _pageData = this.pagerData
    return {
      attr {
        flex(1f)
        backgroundColor(0xFF245df7) // 测试数值
      }
      Header("", "", "", "", "", 32f, _pageData)
    }
  }
}

// 头部
fun ViewContainer<*, *>.Header(resourceImage: String, title: String, author: String, updateTime: String, desc: String, topHeight: Float, pageData: PageData) {
  val mainColor = 0xff000000
  val contentWidth = pageData.pageViewWidth - 20f - 15f - 88f - 15f

  View {
    attr {
      height(180f + topHeight)
      positionAbsolute()
    }
    // 最高层
    View {
      attr {
        absolutePositionAllZero()
        backgroundColor(mainColor)
      }
    }
    // 10% 黑色蒙层
    View {
      attr {
        absolutePositionAllZero()
        backgroundColor(Color(0x1A000000))
      }
    }
    // 渐变层
    View {
      attr {
        absolutePositionAllZero()
        backgroundColor(0xFF000000)
      }
    }

    View {
      attr {
        absolutePositionAllZero()
        paddingTop(topHeight)
      }
      NavigationBar("听书", {}, false)
      View {
        attr {
          padding(20f, 20f, 15f, 15f)
        }

        View {
          attr {
            flexDirectionRow()
            alignItems(FlexAlign.FLEX_START)
          }

          ResourceImage("http://imge.kugou.com/stdmusic/{size}/20250217/20250217223801706860.jpg", true, 977623)
          View {
            attr {
              width(contentWidth)
              height(60f)
              flexDirectionColumn()
              alignItems(FlexAlign.FLEX_START)
            }

            // 标题
            Text {
              attr {
                text("农家傻妻：腹黑皇叔好宠溺|古代甜宠|经商种田")
                width(contentWidth)
                fontSize(16f)
                color(0xFFFFFFFF)
                lines(2)
                // marginTop(-45f)
              }
            }

            // 作者 更新时间
            View {
              attr {
                width(contentWidth)
                flexDirectionRow()
                alignItems(FlexAlign.CENTER)
                marginTop(15f)
                marginBottom(8f)
              }

              Text {
                attr {
                  text("鹿瑶古风有声剧")
                  fontSize(11f)
                  color(0xFFFFFFFF)
                  lines(1)
                }
              }
              Text {
                attr {
                  text("2025年08月31日更新")
                  fontSize(10f)
                  color(0xFFFFFFFF)
                  lines(1)
                  marginLeft(8f)
                  marginRight(11f)
                }
              }
            }
          }
        }
      }
    }
  }
}

// 专辑图片
fun ViewContainer<*, *>.ResourceImage(resource: String, isFinished: Boolean, viewCount: Int) {
  View {
    attr {
      width(88f)
      height(88f)
      marginRight(15f)
    }
    Image {
      attr {
        src(Utils.getSizeImage(400, resource))
        width(88f)
        height(88f)
        borderRadius(4f)
      }
    }
    View {
      attr {
        positionAbsolute()
        left(4f)
        top(4f)
        height(18f)
        paddingLeft(5f)
        paddingRight(5f)
        borderRadius(3f)
        backgroundColor(Color(0x99000000))
        justifyContent(FlexJustifyContent.CENTER)
        alignItems(FlexAlign.CENTER)
      }
      Text {
        attr {
          text(if (isFinished) "完结" else "连载")
          fontSize(10f)
          color(0XFFFFFFFF)
        }
      }
    }
    View {
      attr {
        positionAbsolute()
        right(2f)
        bottom(2f)
        height(18f)
        paddingLeft(7f)
        paddingRight(7f)
        borderRadius(10f)
        backgroundColor(Color(0x80000000))

        flexDirection(FlexDirection.ROW)
        alignItems(FlexAlign.CENTER)
      }

      Image {
        attr {
          src(ImageUri.commonAssets("v20_ic_common_play_2x.png"))
          width(13f)
          height(13f)
        }
      }

      Text {
        attr {
          text(formatViewCount(viewCount))
          fontSize(10f)
          color(Color.WHITE)
          marginLeft(2f)
        }
      }
    }
  }
}

// 导航栏组件
fun ViewContainer<*, *>.NavigationBar(title: String, onBackClick: () -> Unit = {}, isFontWeight: Boolean = false, titleAlignment: FlexJustifyContent? = null) {
  View {
    attr {
      width(100f)
      height(44f)
      flexDirection(FlexDirection.ROW)
      alignItems(FlexAlign.CENTER)
    }
    View {
      attr {
        padding(14f, 15f, 14f, 7f)
      }
      Image {
        attr {
          src(ImageUri.commonAssets("personal_back.png"))
          width(22f)
          height(22f)
          tintColor(Color.BLACK)
        }
      }
      event {
        onBackClick()
      }
    }
    View {
      attr {
        flex(1f)
        justifyContent(titleAlignment ?: FlexJustifyContent.CENTER)
      }
      Text {
        attr {
          text(title)
          color(0xff000000)
          fontSize(17f)
          if (isFontWeight) fontWeight600() else fontWeight400()
        }
      }
    }
  }
}