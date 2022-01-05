import dto.CommentsWithAuthor
import dto.PostWithCommentsAndAuthor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import repository.PostRepository
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    val repository = PostRepository()
    val client = OkHttpClient()
    with(CoroutineScope(EmptyCoroutineContext)) {
        launch {
            try {
                val posts = repository.getPosts(client)
                    .map { post ->
                        async {
                            val postAuthor = repository.getAuthor(client, post.authorId)
                            val comments = repository.getComments(client, post.id).map { comment ->
                                println(comment)
                                CommentsWithAuthor(comment, repository.getAuthor(client, comment.authorId))
                            }
                            PostWithCommentsAndAuthor(
                                post,
                                comments,
                                postAuthor
                            )
                        }
                    }.awaitAll()
                println(posts)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Thread.sleep(1000L)
    }
}