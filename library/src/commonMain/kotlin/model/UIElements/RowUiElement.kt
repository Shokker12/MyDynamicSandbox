package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type
import kotlin.js.JsName

class RowUiElement(    visibility: UiActiveValue<Boolean> = UiActiveValue<Boolean>(true)):UIElement(Type.ROW,visibility) {
    constructor(vararg uiElements: UIElement) : this() {
        uiElements.forEach {
            (list as MutableList<UIElement>).add(list.size,it)
        } //.asArrayList()//.asList()
    }
    val list : MutableList<UIElement> =  mutableListOf<UIElement>()

    constructor(array: ArrayList<UIElement>) : this() {
        array.forEach {
            list.add(list.size,it)        }
    }

    @JsName("getListItems")
    fun getListItems(): Array<UIElement> { return list.toTypedArray() }
}