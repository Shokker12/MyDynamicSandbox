package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type


class FieldUiElement(
    val enabled: UiActiveValue<Boolean> = UiActiveValue(true),
    val value: UiActiveValue<String>,
    val onChange: (String) -> Unit = {  },
    visibility: UiActiveValue<Boolean> = UiActiveValue<Boolean>(true)
):UIElement(Type.TEXTFIELD,visibility)
{

}