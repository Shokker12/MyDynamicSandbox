package ru.ztvr.library.model

import me.ztvr.library.myKonfig.buildInfo
import ru.ztvr.library.consoleOut
import ru.ztvr.library.goDeepLink
import ru.ztvr.library.model.UIElements.*



fun generateExample():UIElement{
    return GeneralExample.createSimpleForm()
}


class GeneralExample {
    companion object {
        fun createSimpleForm():UIElement{
            consoleOut("stating generate UIElements...")

            val loginJS = UiActiveValue<String>("user")
            val passJS = UiActiveValue<String>("pass")
            val responseFlow = UiActiveValue<String>("hello world")
            val boolFlow = UiActiveValue<Boolean>(false)

            val lazyList = UiActiveValue<MutableList<UIElement>>(mutableListOf())


            val field1 = FieldUiElement(boolFlow, value = loginJS, { consoleOut("setting login to $it"); loginJS.value = it})
            var closureIndex = 1

            return ColumnUiElement(
               // ImageUiElement(JSValue("https://s.yimg.com/uu/api/res/1.2/g5MLRpMx9zPNAxRbY37btw--~B/Zmk9c3RyaW07aD0yNzI7cT05NTt3PTU2MDthcHBpZD15dGFjaHlvbg--/https://s.yimg.com/os/creatr-uploaded-images/2022-01/c7b72a30-82c9-11ec-9df9-852f7ca91ddd.cf.webp")),
                ButtonUiElement(TextUiElement(UiActiveValue("RefreshActivity")), onClick = {
                    consoleOut("RefreshActivity button pressed")
                    goDeepLink("http://doesntmatterurl.ru")
                }),
                TextUiElement(UiActiveValue("Login")),
                field1,
                TextUiElement(UiActiveValue( "Password")),
                FieldUiElement(UiActiveValue(true), value = passJS,{ consoleOut("setting pass to $it");  passJS.value = it }),
                ButtonUiElement(TextUiElement(UiActiveValue("Press Me")), onClick =  {
                    consoleOut("button pressed")
                    consoleOut("passJS is ${passJS.value}")
                    if(passJS.value=="12345")
                        responseFlow.value = ("Login passed")
                    else {
                        responseFlow.value = ("Login failed. Try again")
                        passJS.value = ""
                    }
                    consoleOut("login ${loginJS.value} password ${passJS.value} response ${responseFlow.value}")
                }),
                ButtonUiElement(TextUiElement(UiActiveValue("Change state")), onClick = {
                    boolFlow.value = !boolFlow.value
                    passJS.value = "12345"
                    field1.visibility.value = !boolFlow.value
                    consoleOut("state changed to ${boolFlow.value}")
                }),
                ButtonUiElement(TextUiElement(UiActiveValue("add element")), onClick = {
                    consoleOut("add lazy column element")
                    val buttonVisibility = UiActiveValue(true)
                    var index = closureIndex
                    val currentButton = ButtonUiElement(TextUiElement(UiActiveValue("#${closureIndex++} delete one")),
                        {
                            consoleOut("deleteing $index")
                            lazyList.value.removeAt(0)
                            lazyList.value = lazyList.value
                        },
                        buttonVisibility)
                    lazyList.value.add(currentButton)

                    lazyList.value = lazyList.value
                }),
                TextUiElement(responseFlow),
                LazyColumnUiElement(lazyList),
                TextUiElement(UiActiveValue<String>("build info ${buildInfo}"))
            )
        }

    }




}