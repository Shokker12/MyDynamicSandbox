package ru.ztvr.library

actual sealed external interface MySealedInterface {
    actual fun getValue(): String
    actual fun setValue(value: String)
}