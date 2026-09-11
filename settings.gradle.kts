pluginManagement {
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
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  repositories {
    // Kuikly的core/core-annotations会从这里
    maven("https://mirrors.tencent.com/nexus/repository/maven-tencent/")
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/public")
    google()
    mavenCentral()
  }
}
rootProject.name = "songListPage"
include(":androidApp")
include(":shared")
include(":h5App")
include(":miniApp")