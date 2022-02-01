package ru.ztvr.library

expect sealed interface MySealedInterface {
    fun getValue():String
    fun setValue(value:String)
}