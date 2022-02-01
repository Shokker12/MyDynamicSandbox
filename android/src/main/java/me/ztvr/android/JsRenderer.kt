package me.ztvr.android

import androidx.activity.ComponentActivity
import com.eclipsesource.v8.*
import ru.ztvr.library.model.UiActiveValue
import ru.ztvr.library.model.UIElements.*
import ru.ztvr.library.model.type.Type
import java.util.*
import kotlin.collections.ArrayList

class JsRenderer(val activity: ComponentActivity) {
    fun RunJs(script:String): UIElement {
        try {

            v8.registerJavaMethod(printCallback, "consoleOut")
            v8.registerJavaMethod(printCallback, "println")
            v8.registerJavaMethod(activityRefresh, "goDeepLink")
            v8.executeVoidScript(script)

            var printerParent = findObject(v8, "consoleOut", 12)
            println(printerParent ?: "nothing found")
            var v8Object = v8.getObject("library").getObject("ru")
                .getObject("ztvr").getObject("library").getObject("model")
            var ui = v8Object.executeObjectFunction("generateExample", null)

            var nativeUi = generateUIElementsFromJS(ui)

            return nativeUi
        } catch (e: Exception) {
            println(e.message)
            return TextUiElement(UiActiveValue( e.message.toString()))
        }
    }

    fun generateJSValue(jsValue: V8Object): UiActiveValue<String> {
        val j = UiActiveValue<String>(jsValue.getString("value"))
        val callBackFunctionName = UUID.randomUUID().toString()
        val functionObjectToBeadded = jsValue.registerJavaMethod(
            JavaVoidCallback { _, v8Array ->
                println("java observer callback inline with closure")
                j.value = v8Array[0].toString()
            }
            , callBackFunctionName)

        val args = V8Array(v8).push(jsValue.get(callBackFunctionName))
        jsValue.executeVoidFunction("addObserver",args)
        return j
    }

    fun generateJSValueForBool(jsValue: V8Object): UiActiveValue<Boolean> {
        val j = UiActiveValue<Boolean>( true)
        val callBackFunctionName = UUID.randomUUID().toString()
        val functionObjectToBeadded = jsValue.registerJavaMethod(
            JavaVoidCallback { _, v8Array ->
                println("java observer callback inline with closure for boolean")
                j.value = v8Array[0].toString().toBoolean()
            }
            , callBackFunctionName)

        val args = V8Array(v8).push(jsValue.get(callBackFunctionName))
        jsValue.executeVoidFunction("addObserver",args)
        return j
    }

    private fun generateJSValueListUis(jsValue: V8Object): UiActiveValue<MutableList<UIElement>> {
        val j = UiActiveValue<MutableList<UIElement>>( mutableListOf());
        val callBackFunctionName = UUID.randomUUID().toString()

        jsValue.registerJavaMethod(
            JavaVoidCallback { _, v8Array ->
                println("java observer callback inline with closure for LIST ${v8Array.length()}")
                /*val count = (v8Array[0] as V8Object).executeIntegerFunction("count",null)
                println("list with $count elements")*/
                val ml =  mutableListOf<UIElement>()
                val jsIterator = (v8Array[0] as V8Object).executeObjectFunction("iterator", null)
                while (jsIterator.executeBooleanFunction("hasNext", null)) {
                    val element = jsIterator.executeObjectFunction("next",null)
                    ml.add(generateUIElementsFromJS(element))
                    println("parsing element $element")
                }

                j.value = ml
                println(j.value.count())
            }
            , callBackFunctionName)

        val args = V8Array(v8).push(jsValue.get(callBackFunctionName))
        jsValue.executeVoidFunction("addObserver",args)
        return j
    }


    fun generateUIElementsFromJS(jsUiElement: V8Object): UIElement {
        val visibility = generateJSValueForBool(jsUiElement.getObject("visibility"))
        val uiType = getUiType(jsUiElement)
        when (uiType) {
            Type.TEXT -> {
                val flowJsElement = jsUiElement.getObject("text")
                val text = generateJSValue(flowJsElement)
                return TextUiElement(text)
            }

            Type.BUTTON -> {
                return ButtonUiElement(
                    generateUIElementsFromJS(jsUiElement.getObject("innerText")) as TextUiElement,
                    { jsUiElement.executeFunction("onClick", null) })
            }
            Type.TEXTFIELD -> {
                return FieldUiElement(
                    generateJSValueForBool(jsUiElement.getObject("enabled")),
                    //JSValue(true),
                    generateJSValue(jsUiElement.getObject("value")),
                    {
                        val args = V8Array(v8).push(it)
                        jsUiElement.executeFunction("onChange", args)
                        println("passing args ${it} to JS")
                    },visibility)
            }

            Type.COLUMN -> {
                val uiArray = ArrayList<UIElement>()
                var array = jsUiElement.executeArrayFunction("getListItems", null)
                for (i in 0 until array.length()) {
                    uiArray.add(generateUIElementsFromJS(array[i] as V8Object))
                }
                return ColumnUiElement(uiArray)
            }
            Type.ROW -> {
                val uiArray = ArrayList<UIElement>()
                var array = jsUiElement.executeArrayFunction("getListItems", null)
                for (i in 0 until array.length()) {
                    uiArray.add(generateUIElementsFromJS(array[i] as V8Object))
                }
                return RowUiElement(uiArray)
            }
            Type.LAZYCOLUMN -> {
                var list = UiActiveValue<MutableList<UIElement>>(mutableListOf())
                list = generateJSValueListUis(jsUiElement.getObject("lazyList"))
                return LazyColumnUiElement(list,visibility)
            }
            Type.LAZYROW -> {
                var list = UiActiveValue<MutableList<UIElement>>(mutableListOf())
                list = generateJSValueListUis(jsUiElement.getObject("lazyList"))
                return LazyRowUiElement(list,visibility)
            }
            Type.IMAGE -> {
                return ImageUiElement(UiActiveValue("https://s.yimg.com/rz/p/yahoo_homepage_en-US_s_f_p_bestfit_homepage.png"))
            }
        }

        return TextUiElement(UiActiveValue("Element not found"))
    }



    fun getUiType(jsUiElement: V8Object): Type {
        val elementType = jsUiElement.getObject("uiElementType").toString()
        return Type.valueOf(elementType)
        throw Exception("Type $elementType not found")

    }

    var activityRefresh = JavaVoidCallback { reciever, parameters ->
        println("Refreshing activity...")
        activity.finish();
        activity.startActivity(activity.intent);
    }

    var printCallback = JavaVoidCallback { receiver, parameters ->
        if (parameters.length() > 0) {
            val arg1 = parameters[0]
            println(arg1)
            if (arg1 is Releasable) {
                arg1.release()
            }
        } else {
            println("printer called with no params")
        }
    }

    val setTimoutCallback = JavaVoidCallback { receiver, parameters ->
        println("setTimoutCallback")
        (parameters[0] as V8Function).call(null,null)
    }

    val onValueUpdateCallback = JavaVoidCallback { receiver, parameters ->
        println("onValueUpdateCallback")

    }

    private fun findObject(obj: V8Object, objectName: String, level: Int = 0): V8Object? {

        if (level == 0)
            return null
        for (key: String in obj.keys) {
            println(" ".repeat((12 - level) * 2) + key)
            if (key == objectName)
                return obj//.getObject(key)
            else
                try {
                    var f = findObject(obj.getObject(key), objectName, level - 1)
                    if (f != null)
                        return f
                } catch (e: Exception) {
                    //println(" $key error ${e.message} on object $obj")
                }
        }
        return null
    }

    var v8 = V8.createV8Runtime()


}