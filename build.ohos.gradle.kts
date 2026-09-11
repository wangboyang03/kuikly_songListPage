plugins {
  id("com.android.application")
    .version("7.4.2")
    .apply(false)

  id("com.android.library")
    .version("7.4.2")
    .apply(false)

  kotlin("android")
    .version("2.0.21-KBA-010")
    .apply(false)

  kotlin("multiplatform")
    .version("2.0.21-KBA-010")
    .apply(false)

  id("com.google.devtools.ksp")
    .version("2.0.21-1.0.28")
    .apply(false)
}

buildscript {
  repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    google()
    mavenCentral()
  }

  dependencies {
    classpath(
      "com.tencent.kuikly-open:core-gradle-plugin:2.7.0-2.1.21"
    )
  }
}