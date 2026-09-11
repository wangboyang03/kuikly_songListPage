package com.example.song_list_page

import android.app.Application

class KRApplication : Application() {

  init {
    application = this
  }

  companion object {
    lateinit var application: Application
  }
}