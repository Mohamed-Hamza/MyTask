package com.mohamedhamza.mytask.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.mohamedhamza.mytask.R
import com.mohamedhamza.mytask.app.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewTitle: TextView
    private lateinit var textViewBody: TextView
    private lateinit var textViewPostId: TextView
    private val viewModel: PostViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.d_progress_bar)
        textViewTitle = view.findViewById(R.id.textViewTitle)
        textViewBody = view.findViewById(R.id.textViewBody)
        textViewPostId = view.findViewById(R.id.textViewPostId)


        val postId = arguments?.getInt("postId")
        if (postId != null) {
            viewModel.fetchPostDetails(postId)


            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            viewModel.error.observe(viewLifecycleOwner) { error ->
                if (error != null) {
                    view.findViewById<LinearLayout>(R.id.error_layout).visibility = View.VISIBLE
                    view.findViewById<Button>(R.id.try_again_button).setOnClickListener {
                        viewModel.fetchPostDetails(postId)
                    }
                    textViewTitle.visibility = View.GONE
                    textViewBody.visibility = View.GONE
                    textViewPostId.visibility = View.GONE

                } else {
                    view.findViewById<LinearLayout>(R.id.error_layout).visibility = View.GONE
                    textViewTitle.visibility = View.VISIBLE
                    textViewBody.visibility = View.VISIBLE
                    textViewPostId.visibility = View.VISIBLE
                }
            }


            viewModel.selectedPost.observe(viewLifecycleOwner) { post ->

                textViewTitle.text = post.title
                textViewBody.text = post.body
                textViewPostId.text = "Post #${post.id}"
            }
        }


    }

}