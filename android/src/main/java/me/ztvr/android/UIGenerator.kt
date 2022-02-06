package me.ztvr.android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import coil.compose.rememberImagePainter
import ru.ztvr.library.model.GeneralExample
import ru.ztvr.library.model.UIElements.*
import ru.ztvr.library.model.type.Type

class UIGenerator {
    @Preview
    @Composable
    fun generateFromSample(uiElement: UIElement = GeneralExample.createSimpleForm()) {
        val visiblity = uiElement.visibility.flow.collectAsState()
        if(!visiblity.value)
            return
        Box(modifier = Modifier.padding(4.dp, 4.dp, 4.dp).fillMaxWidth())
        {
            when (uiElement.uiElementType) {
                Type.TEXT -> {
                    val textUiElement = (uiElement as TextUiElement)
                    if(textUiElement.text.flow!=null){
                        var text = textUiElement.text.flow.collectAsState("")
                        Text( text.value )
                    } else {
                        Text( textUiElement.text.value )
                    }
                }
                Type.BUTTON -> {
                    val buttonUiElement = (uiElement as ButtonUiElement)
                    Button(onClick =  { buttonUiElement.onClick() }) {
                        generateFromSample(buttonUiElement.innerText)
                    }
                }
                Type.TEXTFIELD -> {
                    val textFieldUi = (uiElement as FieldUiElement)
                    val _enabled =  (textFieldUi.enabled.flow.collectAsState())
                    val tf =  (textFieldUi.value.flow.collectAsState(""))
                    TextField( tf.value,   {
                        textFieldUi.onChange(it)
                        }, enabled = _enabled.value)
                }

                Type.COLUMN -> {
                    val items = (uiElement as ColumnUiElement).list
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for (item in items) {
                            generateFromSample(item)
                        }
                    }
                }
                Type.ROW -> {
                    val items = (uiElement as RowUiElement).list
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (item in items) {
                            generateFromSample(item)
                        }
                    }
                }
                Type.LAZYCOLUMN -> {
                    val lazyColumn = uiElement as LazyColumnUiElement
                    val stateList =  lazyColumn.lazyList.flow.collectAsState()
                    println("starting lazy column")
                    LazyColumn {
                        items(stateList.value) { it ->
                            generateFromSample(it)
                            println("generating item $it ")
                        }
                    }
                }
                Type.LAZYROW -> {
                    val lazyColumn = uiElement as LazyRowUiElement
                    val stateList =  lazyColumn.lazyList.flow.collectAsState()
                    println("starting lazy row")
                    LazyColumn {
                        items(stateList.value) { it ->
                            generateFromSample(it)
                            println("generating item $it ")
                        }
                    }
                }
                Type.IMAGE -> {
                    /*Image(
                        painter = rememberImagePainter("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"),
                        contentDescription = null
                    )*/
                }
            }
        }
    }
}