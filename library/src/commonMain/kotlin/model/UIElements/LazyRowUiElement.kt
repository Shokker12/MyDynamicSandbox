package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type

class LazyRowUiElement(
    val lazyList: UiActiveValue<MutableList<UIElement>>,
    visibility: UiActiveValue<Boolean> = UiActiveValue<Boolean>(true)
):UIElement(Type.LAZYCOLUMN,visibility) {
}
