package com.mohamedhamza.mytask.di

import com.mohamedhamza.mytask.data.PostsApi
import com.mohamedhamza.mytask.domain.PostRepository
import com.mohamedhamza.mytask.domain.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePostsApi(): PostsApi {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostsApi): PostRepository {
        return PostRepositoryImpl(api)
    }
}