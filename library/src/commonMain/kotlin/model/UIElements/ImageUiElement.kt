package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type

class ImageUiElement(val url: UiActiveValue<String> = UiActiveValue(""),
                     visibility: UiActiveValue<Boolean> = UiActiveValue<Boolean>(true)
):UIElement(Type.IMAGE,visibility) {
}