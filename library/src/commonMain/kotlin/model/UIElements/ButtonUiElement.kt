package ru.ztvr.library.model.UIElements

import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.type.Type


class ButtonUiElement (val innerText: TextUiElement, val onClick: ()-> Unit,
                       visibility: UiActiveValue<Boolean> = UiActiveValue<Boolean>(true)
):UIElement(Type.BUTTON,visibility) {

}