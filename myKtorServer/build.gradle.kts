plugins {
    kotlin("multiplatform")
    application //to run JVM part
//    kotlin("plugin.serialization") version "1.4.0"
}
val ktorVersion: String by rootProject.extra

group = "me.ztvr"
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
       withJava()
        application {
            mainClass.set("ServerKt")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }

        val jvmMain by getting {
            dependencies {
                dependencies {
                    implementation("io.ktor:ktor-serialization:$ktorVersion")
                    implementation("io.ktor:ktor-server-core:$ktorVersion")
                    implementation("io.ktor:ktor-server-netty:$ktorVersion")
                    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                    /*implementation("ch.qos.logback:logback-classic:$logbackVersion")
                    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")*/
                }
            }
        }


}


}
