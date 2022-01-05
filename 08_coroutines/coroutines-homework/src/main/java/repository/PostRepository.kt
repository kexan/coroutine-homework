package repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dto.Author
import dto.Comment
import dto.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PostRepository {

    private val BASE_URL = "http://192.168.0.2:9999"

    private val gson = Gson()

    private suspend fun OkHttpClient.apiCall(url: String): Response {
        return suspendCoroutine { continuation ->
            Request.Builder()
                .url(url)
                .build()
                .let(::newCall)
                .enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        continuation.resume(response)
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resumeWithException(e)
                    }
                })
        }
    }

    private suspend fun <T> makeRequest(url: String, client: OkHttpClient, typeToken: TypeToken<T>): T =
        withContext(Dispatchers.IO) {
            client.apiCall(url)
                .let { response ->
                    if (!response.isSuccessful) {
                        response.close()
                        throw RuntimeException(response.message)
                    }
                    val body = response.body ?: throw RuntimeException("response body is null!")
                    gson.fromJson(body.string(), typeToken.type)
                }
        }

    suspend fun getPosts(client: OkHttpClient): List<Post> =
        makeRequest("$BASE_URL/api/posts", client, object : TypeToken<List<Post>>() {})

    suspend fun getComments(client: OkHttpClient, id: Long): List<Comment> =
        makeRequest("$BASE_URL/api/posts/$id/comments", client, object : TypeToken<List<Comment>>() {})

    suspend fun getAuthor(client: OkHttpClient, authorId: Long): Author =
        makeRequest("$BASE_URL/api/authors/$authorId", client, object : TypeToken<Author>() {})
}