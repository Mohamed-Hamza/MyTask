package com.mohamedhamza.mytask.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{postId}")
    suspend fun getPostDetails(@Path("postId") postId: Int): Post
}
