package com.example.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.view.adapter.ToDoAdapter
import com.example.myapplication.view_model.ToDoViewModel

class ToDoFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoFragment()
    }

    private lateinit var viewModel: ToDoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ToDoAdapter
    private lateinit var itemList: List<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}