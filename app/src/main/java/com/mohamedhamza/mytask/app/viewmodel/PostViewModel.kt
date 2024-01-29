package com.mohamedhamza.mytask.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedhamza.mytask.data.Post
import com.mohamedhamza.mytask.domain.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    private val _selectedPost = MutableLiveData<Post>()
    val selectedPost: LiveData<Post> get() = _selectedPost

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> get() = _error

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _posts.value = repository.getPosts()
            } catch (e: Exception) {
                _error.value = "Failed to fetch: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchPostDetails(postId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _selectedPost.value = repository.getPostDetails(postId)
            } catch (e: Exception) {
                _error.value = "Failed to fetch post: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}