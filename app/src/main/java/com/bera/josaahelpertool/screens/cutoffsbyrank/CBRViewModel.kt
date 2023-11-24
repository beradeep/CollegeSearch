package com.bera.josaahelpertool.screens.cutoffsbyrank

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.josaahelpertool.models.CutoffItem
import com.bera.josaahelpertool.use_cases.GeneratePdfUseCase
import com.bera.josaahelpertool.use_cases.GetCutoffsUseCase
import com.bera.josaahelpertool.utils.Constants.IIT_STRING
import com.bera.josaahelpertool.utils.Constants.IIT_STRING_1
import com.bera.josaahelpertool.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CBRViewModel @Inject constructor(
    private val getCutoffsUseCase: GetCutoffsUseCase,
    private val generatePdfUseCase: GeneratePdfUseCase
) : ViewModel() {

    var state by mutableStateOf(CBRState())
        private set
    private lateinit var cutoffs: ArrayList<CutoffItem>

    init {
        getCutoffs()
    }

    fun onAction(action: CBRAction) {
        when (action) {
            is CBRAction.Rank -> updateRank(action.rank)
            is CBRAction.Gender -> updateGender(action.gender)
            is CBRAction.Quota -> updateQuota(action.quota)
            is CBRAction.State -> updateState(action.state)
            is CBRAction.Exam -> updateExam(action.exam)
            is CBRAction.PwD -> updatePwD(action.pwd)
            is CBRAction.ExpandFilters -> updateExpandedState()
            is CBRAction.Sort -> updateSortBy(action.sortBy)
            is CBRAction.GeneratePdf -> generatePdf()
        }
        updateCutoffs(state.rank, state.exam, state.gender, state.pwd, state.quota, state.state)
    }

    private fun generatePdf() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                generatePdfUseCase(cutoffs, state)
            }
        }
    }

    private fun getCutoffs() {
        getCutoffsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {}
                is Resource.Loading -> {
                    state = state.copy(loading = true)
                }

                is Resource.Success -> {
                    cutoffs = result.data!!
                    state = state.copy(cutoffs = cutoffs)
                    state = state.copy(loading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateCutoffs(
        rank: Int,
        exam: String,
        gender: String,
        pwd: Boolean,
        quota: String,
        st: String
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val filterCutoffs = ArrayList<CutoffItem>()
            cutoffs.forEach {
                if (rank < it.ClosingRank.toInt() && (it.Gender.contains(gender) || it.Gender.contains(
                        "Gender-Neutral"
                    )) && (it.SeatType.contains(
                        quota
                    ) || it.SeatType.contains("OPEN"))
                    && (if (pwd) it.SeatType.contains("PwD") else !it.SeatType.contains(
                        "PwD"
                    ))
                    && (if (exam == "Adv") it.Institute.contains(IIT_STRING) || it.Institute.contains(
                        IIT_STRING_1
                    ) else !it.Institute.contains(
                        IIT_STRING
                    ) && it.Quota == st)
                ) {
                    filterCutoffs.add(it)
                }
            }
            filterCutoffs.sortBy {
                when (state.sortBy) {
                    "CR" -> it.ClosingRank.toInt()
                    else -> it.OpeningRank.toInt()
                }
            }
            state = state.copy(cutoffs = filterCutoffs)
        }
    }

    private fun updateExam(exam: String) {
        state = state.copy(exam = exam)
    }

    private fun updateState(st: String) {
        state = state.copy(state = st)
    }

    private fun updateQuota(quota: String) {
        state = state.copy(quota = quota)
    }

    private fun updateGender(gender: String) {
        state = state.copy(gender = gender)
    }

    private fun updateRank(rank: Int) {
        state = state.copy(rank = rank)
    }

    private fun updatePwD(pwd: Boolean) {
        state = state.copy(pwd = pwd)
    }

    private fun updateExpandedState() {
        state = state.copy(expandFilters = !state.expandFilters)
    }

    private fun updateSortBy(sortBy: String) {
        state = state.copy(sortBy = sortBy)
    }

}

