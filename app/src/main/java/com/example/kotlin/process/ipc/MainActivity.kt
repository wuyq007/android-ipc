package com.example.kotlin.process.ipc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.process.ipc.aidl.AidlActivity
import com.example.kotlin.process.ipc.binder.BinderActivity
import com.example.kotlin.process.ipc.databinding.ActivityMainBinding
import com.example.kotlin.process.ipc.messenger.MessengerActivity
import com.example.kotlin.process.ipc.provider.ContentProviderActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.contentBinder.setOnClickListener {
            startActivity(Intent(this, BinderActivity::class.java))
        }


        binding.aidl.setOnClickListener {
            startActivity(Intent(this, AidlActivity::class.java))
        }

        binding.messenger.setOnClickListener {
            startActivity(Intent(this, MessengerActivity::class.java))
        }


        binding.contentProvider.setOnClickListener {
            startActivity(Intent(this, ContentProviderActivity::class.java))
        }
    }

}