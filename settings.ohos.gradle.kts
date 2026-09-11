pluginManagement {
  repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    maven("https://mirrors.tencent.com/nexus/repository/gradle-plugins/")
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")

    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/public")

    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    mavenLocal()

    maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")

    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/public")

    google()
    mavenCentral()
  }
}

rootProject.name = "songListPage-ohos"
rootProject.buildFileName = "build.ohos.gradle.kts"

include(":shared")
project(":shared").projectDir = file("shared")
project(":shared").buildFileName = "build.ohos.gradle.kts"