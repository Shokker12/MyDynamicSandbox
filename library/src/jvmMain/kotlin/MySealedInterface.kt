package ru.ztvr.library

actual sealed interface MySealedInterface {
    actual fun getValue(): String
    actual fun setValue(value: String)
}