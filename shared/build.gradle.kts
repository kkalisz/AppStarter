plugins {
    kotlin("multiplatform")
    id("co.touchlab.native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform-network-generator")
}

android {
    compileSdkVersion(Versions.compile_sdk)
    defaultConfig {
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

kotlin {
    android()
    // Revert to just ios() when gradle plugin can properly resolve it
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    version = "1.1"

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("ios").compilations["main"].kotlinOptions.freeCompilerArgs += "-Xobjc-generics"

    sourceSets["commonMain"].dependencies {
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.SqlDelight.coroutinesExtensions)
        implementation(Deps.Ktor.commonCore)
        implementation(Deps.Ktor.commonJson)
        implementation(Deps.Ktor.commonLogging)
        implementation(Deps.Ktor.ktorClientAuth)
        //implementation(Deps.Ktor.ktorClientAuthLocal)
        implementation(Deps.Coroutines.common) {
            version {
                strictly(Versions.coroutines)
            }
        }
        implementation(Deps.stately)
        implementation(Deps.multiplatformSettings)
        implementation(Deps.koinCore)
        implementation(Deps.Ktor.commonSerialization)
        implementation(Deps.kotlinxDateTime)
        implementation("dev.icerock.moko:network:0.11.0")
        api(Deps.kermit)
    }

    sourceSets["commonTest"].dependencies {
        implementation(Deps.multiplatformSettingsTest)
        implementation(Deps.KotlinTest.common)
        implementation(Deps.KotlinTest.annotations)
        implementation(Deps.koinTest)
        // Karmok is an experimental library which helps with mocking interfaces
        implementation(Deps.karmok)
        implementation(Deps.turbine)
    }

    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.driverAndroid)
        implementation(Deps.Coroutines.android)
        implementation(Deps.Ktor.androidCore)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Deps.KotlinTest.jvm)
        implementation(Deps.KotlinTest.junit)
        implementation(Deps.AndroidXTest.core)
        implementation(Deps.AndroidXTest.junit)
        implementation(Deps.AndroidXTest.runner)
        implementation(Deps.AndroidXTest.rules)
        implementation(Deps.Coroutines.test)
        implementation(Deps.robolectric)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.SqlDelight.driverIos)
        implementation(Deps.Ktor.ios)
    }

    cocoapodsext {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            isStatic = false
            export(Deps.kermit)
            transitiveExport = true
        }
    }
}

sqldelight {
    database("KaMPKitDb") {
        packageName = "co.touchlab.kampkit.db"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
}

mokoNetwork {
    spec("pets") {
        inputSpec = file("src/openapi.json")
    }
//    spec("news") {
//        inputSpec = file("src/newsApi.yaml")
//        packageName = "news"
//        isInternal = false
//        isOpen = true
//        configureTask {
//            // here can be configuration of https://github.com/OpenAPITools/openapi-generator GenerateTask
//        }
//    }
}