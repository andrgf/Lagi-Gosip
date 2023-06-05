package com.example.lagigosip.ui.detailnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.lagigosip.R
import com.example.lagigosip.databinding.ActivityDetailNewsBinding
import com.example.lagigosip.ui.home.HomeViewModel
import com.example.lagigosip.ui.home.ViewModelFactory

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        val content = intent.getStringExtra(ID) ?: ""

        val bundle = Bundle()
        bundle.putString(ID, content)

        viewModel.getHeadlineNews().observe(this) { result ->
            when(result) {
                is com.example.lagigosip.data.Result.Error -> {
                    Toast.makeText(this, "eror", Toast.LENGTH_SHORT).show()
                }
                is com.example.lagigosip.data.Result.Succes -> {
                    val data = result.data
                    binding.tvContent.text = data.toString()
                }
                is com.example.lagigosip.data.Result.Loading -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    companion object {
        const val TITLE = "TITLE"
        const val IMG = "IMG"
        const val ID = "ID"
    }
}