package com.asknilesh.preferencesdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.asknilesh.preferencesdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  lateinit var prefManager: PrefManager
  lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    prefManager = PrefManager(this)

    binding.btnSave.setOnClickListener {
      saveUserDetails()
    }
    observePrefData()
  }

  private fun observePrefData() {
    prefManager.userNameFlow.asLiveData().observe(this) { userName ->
      binding.tvUserName.text = userName
    }

    prefManager.userEmailFlow.asLiveData().observe(this) { userEmail ->
      binding.tvUserEmail.text = userEmail
    }

    prefManager.userAgeFlow.asLiveData().observe(this) { userAge ->
      binding.tvUserAge.text = userAge
    }
  }

  private fun saveUserDetails() {
    if (binding.edtUserName.text.isNullOrEmpty()) {
      return
    }

    if (binding.edtUserEmail.text.isNullOrEmpty()) {
      return
    }

    if (binding.edtUserAge.text.isNullOrEmpty()) {
      return
    }

    GlobalScope.launch {
      prefManager.storeUser(
        age = binding.edtUserAge.text.toString(),
        name = binding.edtUserName.text.toString(),
        email = binding.edtUserEmail.text.toString()
      )
    }
  }
}