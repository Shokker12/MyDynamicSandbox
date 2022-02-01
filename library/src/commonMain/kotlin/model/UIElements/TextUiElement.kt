package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type


class TextUiElement(val text: UiActiveValue<String> = UiActiveValue(""),
                    visibility: UiActiveValue<Boolean> = UiActiveValue<Boolean>(true)):UIElement(Type.TEXT,visibility) {
}