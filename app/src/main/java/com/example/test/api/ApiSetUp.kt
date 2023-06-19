package com.example.test.api

import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http2.Http2Reader
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiSetUp {
    companion object {
        fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addNetworkInterceptor(LoggingInterceptor())
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .build()
        }

        internal class LoggingInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request()
                val t1 = System.nanoTime()
                Http2Reader.logger.info(
                    java.lang.String.format(
                        "Sending request %s on %s%n%s",
                        request.url, chain.connection(), request.headers
                    )
                )
                val response: Response = chain.proceed(request)
                val t2 = System.nanoTime()
                Http2Reader.logger.info(
                    java.lang.String.format(
                        "Received response for %s in %.1fms%n%s",
                        response.request.url, (t2 - t1) / 1e6, response.headers
                    )
                )
                return response
            }
        }

        inline fun <reified T> createRetrofit(okHttpClient: OkHttpClient, serverUrl: String="http://10.0.2.2:3000"): T {

            val retrofit = Retrofit.Builder()
                .baseUrl(serverUrl)                                     //設定請求URL
                .client(okHttpClient)                                   //設定OkHttp攔截器
                .addConverterFactory(GsonConverterFactory.create())     //設定解析工具，這裡使用Gson解析，你也可以使用Jackson等
                .build()

            return retrofit.create(T::class.java)
        }
    }
}