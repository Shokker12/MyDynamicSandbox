package ru.ztvr.library

import IMyModule
import ru.ztvr.library.model.generateExample

class Greeting:IMyModule {
    fun greeting():String{
        return "Hello world from " + getSource()
    }

    override fun doSomething() {
        TODO("Not yet implemented")
    }
}

expect fun getSource():String

fun main(args: Array<String>) {
    var g = Greeting()
    g.greeting()
    println("hello world. hello Alex")
    consoleOut("hello from main")
   // getUi()
    generateExample()
}