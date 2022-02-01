package ru.ztvr.library.model

import kotlinx.coroutines.flow.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ru.ztvr.library.consoleOut
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty


class FlowOrValue<T>(val flow: MutableStateFlow<T>?=null, val value:T? = null ):ObservableProperty<T>(
    value?:flow!!.value)
    {
    init {
        if(flow==null && value==null)
            Exception("flow and value cannot be null at the same time for ValOrFlow")

    }
        constructor(flow:MutableStateFlow<T>) : this(flow,null)
        constructor(value:T) : this(null,value)


        fun setValueToFlow(newValue:T){
        flow?.tryEmit(newValue)
        consoleOut("emitting value ${newValue.toString()}")
    }

        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            super.afterChange(property, oldValue, newValue)
            if(flow!=null)
                flow.value = newValue
        }
}

