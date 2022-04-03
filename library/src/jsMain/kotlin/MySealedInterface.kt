package ru.ztvr.library

import io.ktor.client.engine.js.*

actual sealed external interface MySealedInterface {
    actual fun getValue(): String
    actual fun setValue(value: String)
}

fun doSmth(){
    val client = JsClient()
}