package com.example.suitmediamobiletest.ui.thirdscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmediamobiletest.R
import com.example.suitmediamobiletest.data.response.DataItem
import com.example.suitmediamobiletest.databinding.ActivityThirdBinding
import com.example.suitmediamobiletest.ui.adapter.LoadingStateAdapter
import com.example.suitmediamobiletest.ui.adapter.UserAdapter
import com.example.suitmediamobiletest.ui.factory.ViewModelFactory

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private val factory = ViewModelFactory.getInstance()
    private val viewModel: ThirdViewModel by viewModels {
        factory
    }

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

        supportActionBar?.show()
        supportActionBar?.let { actionbar ->
            actionbar.title = "Third Screen"

            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.third_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        getData()
    }

    private fun getData() {
        val adapter = UserAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            },
            header = LoadingStateAdapter {
                adapter.refresh()
            }
        )
        viewModel.getUsers().observe(this) {
            adapter.submitData(lifecycle, it)
            binding.swipeRefresh.isRefreshing = false
            binding.emptyStateText.isVisible = adapter.itemCount == 0
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Error) {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setOnItemClickListener(onItemClickListener = object :
            UserAdapter.OnItemClickListener {
            override fun onItemClick(item: DataItem) {
                val username = item.firstName + " " + item.lastName
                val resultIntent = Intent()
                resultIntent.putExtra("username", username)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        })

        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}