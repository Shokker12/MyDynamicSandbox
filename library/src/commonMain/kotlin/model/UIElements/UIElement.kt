package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type

sealed class UIElement(val uiElementType: Type,val visibility: UiActiveValue<Boolean> = UiActiveValue(true)){

}

