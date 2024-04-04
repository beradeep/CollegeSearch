package com.bera.josaahelpertool.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.josaahelpertool.R
import com.bera.josaahelpertool.models.ui.TopHalfItem
import com.bera.josaahelpertool.use_cases.GetQuotesUseCase
import com.bera.josaahelpertool.use_cases.GetUniversityImagesUseCase
import com.bera.josaahelpertool.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getUniversityImagesUseCase: GetUniversityImagesUseCase
) : ViewModel() {

    val drawableIds =
        arrayOf(
            R.drawable.img_5,
            R.drawable.img_2,
            R.drawable.img_6,
            R.drawable.img_1
        )
    private val _slideImages = MutableStateFlow<List<TopHalfItem>>(emptyList())
    val slideImages get() = _slideImages.asStateFlow()

    data class Link(
        val link: String,
        val imageRes: Int,
        val text: String
    )

    val links = arrayOf(
        Link(
            link = "https://jeemain.nta.nic.in/",
            imageRes = R.drawable.jee_main,
            text = "JEE Main"
        ),
        Link(
            link = "https://jeeadv.ac.in/",
            imageRes = R.drawable.jee_adv,
            text = "JEE Adv"
        ),
        Link(
            link = "https://josaa.nic.in/",
            imageRes = R.drawable.josaa,
            text = "JoSAA"
        ),
        Link(
            link = "https://csab.nic.in/",
            imageRes = R.drawable.csab,
            text = "CSAB"
        )
    )

    val imageTexts = arrayOf(
        "IIIT Allahabad",
        "IIT Delhi",
        "NIT Rourkela",
        "IIT Bombay"
    )

    private fun fetchUniversityCampusImages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val allImages = getUniversityImagesUseCase.getAllImages()

                _slideImages.value = allImages
            }
        }
    }

    suspend fun changeImagePage(pagerState: PagerState, next: Boolean) {
        pagerState
            .animateScrollToPage(
                if (next) {
                    if (pagerState.currentPage == pagerState.pageCount - 1) 0 else (pagerState.currentPage + 1)
                } else {
                    if (pagerState.currentPage == 0) pagerState.pageCount - 1 else (pagerState.currentPage - 1)
                }
            )
    }

    var quote by mutableStateOf("")
    var author by mutableStateOf("")
    var isQuoteLoading by mutableStateOf(false)
        private set

    init {
        getQuotes()
    }

    private fun getQuotes() {
        getQuotesUseCase(category = "education").onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    isQuoteLoading = true
                    Log.d("loading", "loading")
                }

                is Resource.Error -> {
                    isQuoteLoading = false
                    quote = "Oops! Unable to load.."
                    Log.d("error", "error")
                }

                is Resource.Success -> {
                    isQuoteLoading = false
                    quote = resource.data?.get(0)?.quote ?: ""
                    author = resource.data?.get(0)?.author ?: ""
                    Log.d("Success", "Success")
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        fetchUniversityCampusImages()
    }
}