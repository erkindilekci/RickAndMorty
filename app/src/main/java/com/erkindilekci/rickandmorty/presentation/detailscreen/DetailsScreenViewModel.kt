package com.erkindilekci.rickandmorty.presentation.detailscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.rickandmorty.data.remote.dto.detail.CharacterDetail
import com.erkindilekci.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val repository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedCharacter: MutableStateFlow<CharacterDetail?> = MutableStateFlow(null)
    val selectedCharacter: StateFlow<CharacterDetail?> = _selectedCharacter.asStateFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val id = savedStateHandle.get<String>("id")?.toInt()
            val resource = id?.let { repository.getCharacterDetail(id) }
            _selectedCharacter.value = resource?.data
        }
    }
}
