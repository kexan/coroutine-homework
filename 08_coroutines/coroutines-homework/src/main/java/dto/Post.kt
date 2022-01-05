package dto

import enumeration.AttachmentType

data class Post(
    val id: Long,
    val authorId: Long,
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,
    var attachment: Attachment? = null,
)

data class PostWithCommentsAndAuthor(
    val post: Post,
    val comment: List<CommentsWithAuthor>,
    val author: Author
)

data class Attachment(
    val url: String,
    val description: String,
    val type: AttachmentType,
)



