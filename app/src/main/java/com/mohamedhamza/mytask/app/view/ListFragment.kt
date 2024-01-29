package com.mohamedhamza.mytask.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mohamedhamza.mytask.R
import com.mohamedhamza.mytask.app.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var parentLayout: ConstraintLayout
    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        parentLayout = view.findViewById(R.id.list_fragment_parent)

        adapter = PostsAdapter { post ->
            val action =
                ListFragmentDirections.actionListFragmentToDetailsFragment2(
                    post.id
                )
            findNavController().navigate(action)

        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                view.findViewById<LinearLayout>(R.id.error_layout).visibility = View.VISIBLE
                view.findViewById<Button>(R.id.try_again_button).setOnClickListener {
                    viewModel.fetchPosts()
                }
                recyclerView.visibility = View.GONE
            } else {
                view.findViewById<LinearLayout>(R.id.error_layout).visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.fetchPosts()
    }

}