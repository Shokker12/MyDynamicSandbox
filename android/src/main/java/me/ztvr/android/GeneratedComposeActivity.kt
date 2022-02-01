package me.ztvr.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.ztvr.library.model.GeneralExample
import ru.ztvr.library.model.UIElements.*

import ru.ztvr.library.model.type.Type

class GeneratedComposeActivity: ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            UIGenerator().generateFromSample(GeneralExample.createSimpleForm())
        }

        ///println( Json.encodeToString(GeneralExample.createSimpleForm()) )
    }

@Preview
@Composable
    fun doPreview() {
    UIGenerator().generateFromSample(GeneralExample.createSimpleForm())
    }
}