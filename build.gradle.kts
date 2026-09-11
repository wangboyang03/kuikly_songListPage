plugins {
  //trick: for the same plugin versions in all sub-modules
  id("com.android.application").version("7.4.2").apply(false)
  id("com.android.library").version("7.4.2").apply(false)
  kotlin("android").version("2.1.21").apply(false)
  kotlin("multiplatform").version("2.1.21").apply(false)
  id("com.google.devtools.ksp").version("2.1.21-2.0.1").apply(false)

}

buildscript {
  repositories {
    // Kuikly
    maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")

    // 国内镜像
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/gradle-plugin")

    google()
    mavenCentral()
  }

  dependencies {
    classpath("com.tencent.kuikly-open:core-gradle-plugin:2.7.0-2.1.21")
  }
}