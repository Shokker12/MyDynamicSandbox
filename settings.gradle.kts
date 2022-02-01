pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
rootProject.name = "untitled"


include(":library")
include(":android")
include(":jvm")
include(":otherLibraryModule")
include("myKtorServer")
