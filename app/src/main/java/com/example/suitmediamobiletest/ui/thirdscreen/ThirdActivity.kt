package com.example.suitmediamobiletest.ui.thirdscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmediamobiletest.R
import com.example.suitmediamobiletest.data.response.DataItem
import com.example.suitmediamobiletest.databinding.ActivityThirdBinding
import com.example.suitmediamobiletest.ui.adapter.UserAdapter
import com.example.suitmediamobiletest.ui.factory.ViewModelFactory

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val factory = ViewModelFactory.getInstance()
        val viewModel: ThirdViewModel by viewModels {
            factory
        }

        viewModel.getUsers().observe(this) {
            setupData(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.getUsers().observe(this) {
                setupData(it)
            }
        }

        val rv = binding.recyclerView
        adapter = UserAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

    }

    private fun setupData(userList: List<DataItem>) {
        if(userList.isEmpty()) {
            binding.swipeRefreshLayout.isRefreshing = false
            Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
        } else {
            adapter = UserAdapter()
            adapter.userList = userList
            adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
                override fun onItemClick(item: DataItem) {
                    showToast(item)
                }
            })

            binding.recyclerView.adapter = adapter
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showToast(item: DataItem) {
        Toast.makeText(this, item.firstName, Toast.LENGTH_SHORT).show()
    }

}