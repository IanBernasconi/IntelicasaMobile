import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Api {
    companion object {
        const val BASE_URL = "http://192.168.1.4:8080/api"
        private var isConnected = false

        fun setConnected(value: Boolean) {
            isConnected = value
            // Dispatch event if required
        }

        suspend fun fetchApi(
            url: String,
            headers: Map<String, String> = emptyMap(),
            method: String = "GET",
            body: String? = null
        ): JSONObject? {
            return withContext(Dispatchers.IO) {
                try {
                    val connection = URL(url).openConnection() as HttpURLConnection
                    connection.requestMethod = method

                    headers.forEach { (key, value) ->
                        connection.setRequestProperty(key, value)
                    }

                    if (body != null) {
                        connection.doOutput = true
                        connection.outputStream.use { outputStream ->
                            outputStream.write(body.toByteArray())
                        }
                    }

                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStreamReader = InputStreamReader(connection.inputStream)
                        val bufferedReader = BufferedReader(inputStreamReader)
                        val response = StringBuilder()
                        var inputLine: String?
                        while (bufferedReader.readLine().also { inputLine = it } != null) {
                            response.append(inputLine)
                        }
                        bufferedReader.close()
                        inputStreamReader.close()

                        isConnected = true
                        JSONObject(response.toString())
                    } else {
                        isConnected = false
                        null
                    }
                } catch (e: Exception) {
                    Log.e("Api", "Error: ${e.message}")
                    isConnected = false
                    null
                }
            }
        }

        suspend fun get(url: String): JSONObject? {
            return fetchApi(url)
        }

        suspend fun post(url: String, data: JSONObject): JSONObject? {
            return fetchApi(url, mapOf("Content-Type" to "application/json; charset=utf-8"), "POST", data.toString())
        }

        suspend fun put(url: String, data: String): JSONObject? {
            return fetchApi(url, mapOf("Content-Type" to "application/json; charset=utf-8"), "PUT", data.toString())
        }

        suspend fun delete(url: String): JSONObject? {
            return fetchApi(url, mapOf("Content-Type" to "application/json; charset=utf-8"), "DELETE")
        }
    }
}
