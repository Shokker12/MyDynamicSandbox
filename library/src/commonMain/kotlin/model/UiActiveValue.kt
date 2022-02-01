package ru.ztvr.library.model

import kotlinx.coroutines.flow.MutableStateFlow
import ru.ztvr.library.consoleOut
import kotlin.js.JsName

class UiActiveValue<T>(val initialValue:T)
{
    val observers = mutableListOf<(T) -> Unit>()
    private var _value:T = initialValue
    @JsName("value")
    var value:
            T get():T = _value
        set(_v)
        {
            observers.forEach {
                it(_v)
            }

            flow.value = _v
            _value = _v
            consoleOut("set value call finished. ${_value.toString()}")
        }

    var flow = MutableStateFlow<T>(initialValue)
    fun getCurrentValue():T {
        return value
    }

    @JsName("addObserver")
    fun addObserver(v:(T) -> Unit) {
        consoleOut("adding observer with type "+ this::class.simpleName )
        observers.add(observers.count(),v)
        v.invoke(value)
    }
}

expect fun onValueUpdate(uiActiveValue: UiActiveValue<String>)