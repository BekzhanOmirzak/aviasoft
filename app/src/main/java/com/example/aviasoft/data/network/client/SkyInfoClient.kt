package com.example.aviasoft.data.network.client

import com.example.aviasoft.data.network.authkey.AuthorizationGen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant

class SkyInfoClient {

    private val requestURL = "https://testpos.api.skydepot.io"

    suspend fun makePostNetworkCall(
        methodName: String,
        requestParam: HashMap<String, String>
    ): String? {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        val rts = Instant.now().epochSecond
        val authToken = AuthorizationGen.genkey(rts)
        try {
            val url = URL(requestURL)
            urlConnection = withContext(Dispatchers.IO) {
                url.openConnection()
            } as HttpURLConnection

            urlConnection.requestMethod = "POST"
            urlConnection.setRequestProperty("Content-Type", "application/json")
            urlConnection.setRequestProperty("authorization", authToken)
            urlConnection.setRequestProperty("rts", rts.toString())
            urlConnection.doOutput = true

            val jsonObject = JSONObject()
            jsonObject.put("jsonrpc", "2.0")
            jsonObject.put("method", methodName)
            jsonObject.put("id", 1)
            jsonObject.put("params", JSONObject(requestParam as Map<*, *>))

            urlConnection.outputStream.use { os: OutputStream ->
                val input: ByteArray = jsonObject.toString().toByteArray(Charsets.UTF_8)
                os.write(input, 0, input.size)
            }

            val responseCode = urlConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = StringBuilder()
                reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                reader.useLines { lines -> lines.forEach { response.append(it) } }
                return response.toString()
            } else {
                println("Error on retrieving data: $responseCode")
            }
        } catch (e: Exception) {
            println("Exception in client : $e")
            e.printStackTrace()
            e.toString()
        } finally {
            urlConnection?.disconnect()
            withContext(Dispatchers.IO) {
                reader?.close()
            }
        }
        return null
    }

}