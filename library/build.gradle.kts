import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.codingfeline.buildkonfig")
}

fun getCurrentTimestamp():String
{
    val today =  Date()
    val df =  SimpleDateFormat("MM-dd-yyyy HH-mm")
    return df.format(today)
}

group = "me.ztvr"
version = "1.0-SNAPSHOT"

kotlin {


    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()/*
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }*/
    }
    js(LEGACY) {
        moduleName = "MyModule"
        browser {
            webpackTask {
                output.libraryTarget = "this"  // "this" - was before
                outputFileName = "global.js"
                mode = org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.DEVELOPMENT
                //devtool = org.jetbrains.kotlin.gradle.targets.js.webpack.WebpackDevtool.INLINE_SOURCE_MAP
            }
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(project(":otherLibraryModule"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

            }
        }
        val commonTest by getting {
            /*   dependencies {
                   implementation ("org.jetbrains.kotlin:kotlin-test-junit5:1.3.31")
                   implementation ("org.jetbrains.kotlin:kotlin-test:1.3.31")
               }*/
        }
        val jvmMain by getting {
            dependencies {

            }
        }
        val jvmTest by getting {
            //dependencies {
            //    implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
            //  runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
            // }
        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.0")
            }
        }
        val jsTest by getting
    }

    tasks.register<Copy>("copyJS1") {
        from(layout.buildDirectory.dir("distributions")) { include("*.js") }
        into(layout.buildDirectory.dir("../../android/src/main/res/raw/"))
    }
    tasks.register<Copy>("copyJS2") {
        from(layout.buildDirectory.dir("distributions")) { include("*.js") }
        into(layout.buildDirectory.dir("../../myKtorServer/build/processedResources/jvm/main/js/"))
    }
    tasks.named("build") { finalizedBy("copyJS1","copyJS2") }



    buildkonfig {


        packageName = "me.ztvr.library"
        // objectName = 'YourAwesomeConfig'
        exposeObjectWithName = "myKonfig"

        defaultConfigs {
            buildConfigField(
                Type.STRING
                ,"buildInfo",getCurrentTimestamp())
        }
    }

}





