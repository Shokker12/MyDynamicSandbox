buildscript {
    repositories {
        google()
        mavenCentral()
        maven ( url ="https://jitpack.io" )
        maven ( url= "https://maven.google.com" )
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.android.tools.build:gradle:7.0.0")
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.11.0")
    }
}

group = "me.ztvr"
version = "1.0-SNAPSHOT"


val ktorVersion by extra { "1.6.5" }



allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
}