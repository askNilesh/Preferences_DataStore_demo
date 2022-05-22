package com.asknilesh.preferencesdatastore

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefManager(context: Context) {

  //Create the dataStore
  private val dataStore = context.createDataStore(name = "user_prefs")

  //Create some keys
  companion object {
    val USER_AGE_KEY = preferencesKey<String>("USER_AGE")
    val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
    val USER_EMAIL = preferencesKey<String>("USER_EMAIL")
  }

  //Store user data
  suspend fun storeUser(age: String, name: String, email: String) {
    dataStore.edit {
      it[USER_AGE_KEY] = age
      it[USER_NAME_KEY] = name
      it[USER_EMAIL] = email
    }
  }

  //Create an age flow
  val userAgeFlow: Flow<String> = dataStore.data.map {
    val age = it[USER_AGE_KEY] ?: ""
    age
  }

  //Create a name flow
  val userNameFlow: Flow<String> = dataStore.data.map {
    it[USER_NAME_KEY] ?: ""
  }

  //Create a email flow
  val userEmailFlow: Flow<String> = dataStore.data.map {
    it[USER_EMAIL] ?: ""
  }
}