package com.bera.josaahelpertool.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.josaahelpertool.models.CutoffItem
import com.bera.josaahelpertool.use_cases.GetCutoffsUseCase
import com.bera.josaahelpertool.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCutoffsUseCase: GetCutoffsUseCase,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val allCutoffs = MutableStateFlow(emptyList<CutoffItem>())

    @OptIn(FlowPreview::class)
    val collegeResults = searchText
        .debounce(1000)
        .combine(allCutoffs) { searchText, cutoffs ->
        if (searchText.isBlank()) {
            emptyList()
        } else {
            cutoffs.map { it.Institute }.distinct().filter {
                it.contains(searchText, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


    @OptIn(FlowPreview::class)
    val branchResults = searchText
        .debounce(1000)
        .combine(allCutoffs) { searchText, cutoffs ->
        if (searchText.isBlank()) {
            emptyList()
        } else {
            cutoffs.map { it.AcademicProgramName }.distinct().filter {
                it.contains(searchText, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        getCutoffs()
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    private fun getCutoffs() {
        getCutoffsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Loading -> {
                    _loading.value = true
                }

                is Resource.Success -> {
                    allCutoffs.update { result.data!!.toList() }
                    _loading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}