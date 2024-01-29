package com.mohamedhamza.mytask.domain

import com.mohamedhamza.mytask.data.Post
import com.mohamedhamza.mytask.data.PostsApi
import javax.inject.Inject

class PostRepositoryImpl(private val api: PostsApi) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return api.getPosts()
    }

    override suspend fun getPostDetails(postId: Int): Post {
        return api.getPostDetails(postId)
    }
}