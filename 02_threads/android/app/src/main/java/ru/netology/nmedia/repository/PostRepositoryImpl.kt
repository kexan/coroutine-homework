package ru.netology.nmedia.repository

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.GlobalApplication
import android.app.Activity




class PostRepositoryImpl : PostRepository {

    override fun getAllAsync(callback: PostRepository.RepositoryCallback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(GlobalApplication.appContext, "Something wrong with server, try later", Toast.LENGTH_LONG).show()
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(GlobalApplication.appContext, "Network failure :( Check your connection", Toast.LENGTH_LONG).show()
                callback.onError(RuntimeException(t.message))
            }

        })
    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Post>) {
        PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(GlobalApplication.appContext, "Something wrong with server, try later", Toast.LENGTH_LONG).show()
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(GlobalApplication.appContext, "Network failure :( Check your connection", Toast.LENGTH_LONG).show()
                callback.onError(RuntimeException(t.message))
            }
        })
    }

    override fun unLikeByIdAsync(
        id: Long,
        callback: PostRepository.RepositoryCallback<Post>
    ) {
        PostsApi.retrofitService.unLikeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(GlobalApplication.appContext, "Something wrong with server, try later", Toast.LENGTH_LONG).show()
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(GlobalApplication.appContext, "Network failure :( Check your connection", Toast.LENGTH_LONG).show()
                callback.onError(RuntimeException(t.message))
            }
        })
    }

    override fun saveAsync(post: Post, callback: PostRepository.RepositoryCallback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(GlobalApplication.appContext, "Something wrong with server, try later", Toast.LENGTH_LONG).show()
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(RuntimeException(t.message))
            }
        })
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(GlobalApplication.appContext, "Something wrong with server, try later", Toast.LENGTH_LONG).show()
                    callback.onError(RuntimeException(response.message()))
                    return
                }
                callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(GlobalApplication.appContext, "Network failure :( Check your connection", Toast.LENGTH_LONG).show()
                callback.onError(RuntimeException(t.message))
            }
        })
    }
}