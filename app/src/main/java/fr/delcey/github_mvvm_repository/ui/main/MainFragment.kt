package fr.delcey.github_mvvm_repository.ui.main

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import fr.delcey.github_mvvm_repository.R

class MainFragment : Fragment(), MainRecyclerViewAdapter.OnGithubRepoClickedListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this, ViewModelFactory).get(MainViewModel::class.java)

        val editText = view.findViewById<TextInputEditText>(R.id.main_tiet)
        editText.doAfterTextChanged {
            viewModel.usernameChanged(it.toString())
        }

        val adapter = MainRecyclerViewAdapter(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.main_rv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.main_srl)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.getUiModelLiveData().observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it.isLoading
            adapter.submitList(it.listItems)
        })

        return view
    }

    override fun onGithubRepoClicked(url: String) {
        requireActivity().startActivity(Intent(ACTION_VIEW).setData(((Uri.parse(url)))))
    }
}
