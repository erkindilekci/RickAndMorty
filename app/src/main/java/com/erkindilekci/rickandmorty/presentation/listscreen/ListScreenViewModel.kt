package com.erkindilekci.rickandmorty.presentation.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.erkindilekci.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val pager = repository.getPaginatedCharacters().cachedIn(viewModelScope)
}
