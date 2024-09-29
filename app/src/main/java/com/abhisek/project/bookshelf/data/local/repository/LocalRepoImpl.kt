package com.abhisek.project.bookshelf.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore : DataStore<Preferences> by preferencesDataStore("book_shelf")
class LocalRepoImpl @Inject constructor(private val context: Context) : ILocalRepo {

    companion object {
        val IS_AUTHENTICATED = booleanPreferencesKey("is_authenticated")
    }

    override suspend fun isAuthenticated(): Boolean? = context.dataStore.data.map { preference ->
        preference[IS_AUTHENTICATED]
    }.first()

    override suspend fun setAuthenticated(isAuthenticated: Boolean) {
        context.dataStore.edit { preference ->
            preference[IS_AUTHENTICATED] = isAuthenticated
        }
    }
}