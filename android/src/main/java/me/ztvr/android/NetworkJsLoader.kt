package me.ztvr.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class NetworkJsLoader : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            MainScope().launch {
                val jsCode = loadJs()
                setContent {
                    UIGenerator().generateFromSample(JsRenderer(this@NetworkJsLoader).RunJs(jsCode))
                }
            }
    }

    suspend fun loadJs():String
    {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("http://192.168.0.101:9090/js/global.js")
        println(response.status)
        val responseString:String =  response.receive()
        client.close()
        return responseString
    }
}
