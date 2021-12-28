plugins {
    id("org.jetbrains.kotlin.js") version "1.6.10"
    id("io.miret.etienne.sass") version "1.1.2"
}

group = "io.monosketch.web"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin {
    js {
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}

apply(from = "ktlint.gradle")
apply(from = "sass.gradle")
