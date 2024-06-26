package com.superman.movieticket.ui.profile.control;

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.entities.User
import com.superman.movieticket.infrastructure.utils.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor( private val dataStore: DataStore<Preferences>
): ViewModel() {
    private val _user: MutableStateFlow<User>? = null
    val user: MutableStateFlow<User>? get() = _user
    fun HandleLogout()
    {
        viewModelScope.launch{
            dataStore.edit{ it.remove(PreferenceKey.IS_AUTHENTICATE)
            it.remove(PreferenceKey.ACCESS_TOKEN) }
        }
    }
}
