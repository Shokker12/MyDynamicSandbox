
plugins {
    kotlin("multiplatform")
}

group = "ru.ztvr"
version = "1.0-SNAPSHOT"



kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }

    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = false
            }
        }
    }
    sourceSets {
        val commonMain by getting
       /*val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting*/
    }
}


