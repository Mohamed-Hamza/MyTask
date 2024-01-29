package com.mohamedhamza.mytask.domain

import com.mohamedhamza.mytask.data.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getPostDetails(postId: Int): Post
}