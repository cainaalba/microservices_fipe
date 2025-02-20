package br.com.vda.fipe.interfaces

import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

interface RequestInterface {
    fun request(jsonString: String, endPoint: String): String? {
        val mapHeaders = HashMap<String, String>()
        mapHeaders["Content-Type"] = "application/json"
        val headers = mapHeaders.toHeaders()

        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonString.toRequestBody(jsonMediaType)
        val request: Request = Request.Builder()
            .url(endPoint)
            .post(requestBody)
            .headers(headers)
            .build()

        val response = clientHttp().newCall(request).execute()
        return response.body?.string()
    }

    private fun clientHttp(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }
}