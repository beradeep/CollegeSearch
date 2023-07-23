package com.bera.collegesearch.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.collegesearch.R
import com.bera.collegesearch.use_cases.GetQuotesUseCase
import com.bera.collegesearch.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase
) : ViewModel() {

    val pagerState = PagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )

    val drawableIds =
        arrayOf(
            R.drawable.iit_vector,
            R.drawable.nit_vector,
            R.drawable.iiit_vector,
            R.drawable.gfti_vector
        )

    val slideImage =
        arrayOf(
            R.drawable.iitbombay,
            R.drawable.iit,
            R.drawable.nit,
            R.drawable.ogc
        )

    suspend fun changeImagePage(next: Boolean) {
        pagerState
            .animateScrollToPage(
                if (next) pagerState.currentPage + 1
                else pagerState.currentPage - 1
            )
    }

    var quote by mutableStateOf("")
    var author by mutableStateOf("")

    init {
        getQuotes()
    }

    private fun getQuotes() {
        getQuotesUseCase(category = "education").onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    quote = "The quote of the day is loading.."
                    Log.d("loading", "loading")
                }

                is Resource.Error -> {
                    Log.d("error", "error")
                }

                is Resource.Success -> {
                    quote = resource.data?.get(0)?.quote ?: ""
                    author = resource.data?.get(0)?.author ?: ""
                    Log.d("Success", "Success")
                }
            }
        }.launchIn(viewModelScope)
    }
}