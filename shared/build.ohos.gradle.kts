plugins {
  kotlin("multiplatform")
  kotlin("native.cocoapods")
  id("com.google.devtools.ksp")
  id("maven-publish")
}

val KEY_PAGE_NAME = "pageName"

kotlin {
  ohosArm64 {
    binaries.sharedLib {
      baseName = "shared"
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("com.tencent.kuikly-open:core:${Version.getKuiklyOhosVersion()}")

        implementation("com.tencent.kuikly-open:core-annotations:${Version.getKuiklyOhosVersion()}")
      }
    }
  }
}

group = "com.example.song_list_page"
version = System.getenv("kuiklyBizVersion") ?: "1.0.0"

publishing {
  repositories {
    maven {
      credentials {
        username = System.getenv("mavenUserName") ?: ""
        password = System.getenv("mavenPassword") ?: ""
      }
      rootProject.properties["mavenUr?"]?.toString()?.let { url = uri(it) }
    }
  }
}

ksp {
  arg(KEY_PAGE_NAME, getPageName())
}

dependencies {
  add("kspOhosArm64", "com.tencent.kuikly-open:core-ksp:${Version.getKuiklyOhosVersion()}")
}

fun getPageName(): String {
  return (project.properties[KEY_PAGE_NAME] as? String) ?: ""
}

fun getCommonCompilerArgs(): List<String> {
  return listOf(
    "-Xallocator=std"
  )
}

fun getLinkerArgs(): List<String> {
  return listOf()
}