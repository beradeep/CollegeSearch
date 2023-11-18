package com.bera.josaahelpertool.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.josaahelpertool.models.Cutoff
import com.bera.josaahelpertool.ui.theme.rubikFamily
import com.bera.josaahelpertool.use_cases.GetCutoffsUseCase
import com.bera.josaahelpertool.utils.Constants
import com.bera.josaahelpertool.utils.CustomDivider
import com.bera.josaahelpertool.utils.Resource
import com.bera.josaahelpertool.utils.ShimmerListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@Composable
fun CutoffColumn(
    viewModel: CutoffColumnViewModel,
) {
    val isLoading = viewModel.loading
    val cutoffs = viewModel.finalCutoffs

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .padding(10.dp, 5.dp)
                .fillMaxSize()
        ) {
            item {
                CustomDivider(
                    modifier = Modifier.padding(10.dp),
                    text = "Cutoffs",
                    alignment = Alignment.CenterStart
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = "College: ${viewModel.college}",
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = "Branch: ${viewModel.branch}",
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text = "Quota",
                        fontFamily = rubikFamily,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text = "Gender",
                        fontFamily = rubikFamily,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "SeatType",
                        fontFamily = rubikFamily,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Divider(modifier = Modifier.padding(6.dp))
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(if (isLoading.value) List(20) { Constants.FakeCutoffItem }
            else cutoffs.value) {
                ShimmerListItem(
                    isLoading = isLoading.value,
                    barCount = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .heightIn(min = 80.dp)
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 6.dp),
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = it.Quota,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.width(100.dp))
                                Text(
                                    text = it.Gender.removeSuffix("(including Supernumerary)"),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = it.SeatType,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Opening Rank: ${it.OpeningRank}",
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Closing Rank: ${it.ClosingRank}",
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = Color.LightGray, thickness = 0.5f.dp)
                        }
                    }
                }
            }
        }
    }
}

@HiltViewModel
class CutoffColumnViewModel @Inject constructor(
    private val getCutoffsUseCase: GetCutoffsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _loading = mutableStateOf(true)
    val loading: State<Boolean> = _loading
    private val _cutoffs = mutableStateOf(Cutoff())
    private val cutoffs = Cutoff()
    private val _finalCutoffs = mutableStateOf(cutoffs)
    val finalCutoffs: State<Cutoff> = _finalCutoffs
    var college: String
    var branch: String

    init {
        college = savedStateHandle.get<String>("college")!!
        branch = savedStateHandle.get<String>("branch")!!
        getCutoffs(
            college,
            branch
        )
    }

    private fun getCutoffs(college: String, branch: String) {
        getCutoffsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> _loading.value = false
                is Resource.Loading -> _loading.value = true
                is Resource.Success -> {
                    _cutoffs.value = result.data as Cutoff
                    _cutoffs.value.forEach { cutoffItem ->
                        if (cutoffItem.Institute == college && cutoffItem.AcademicProgramName == branch) {
                            cutoffs.add(cutoffItem)
                        }
                    }
                    _finalCutoffs.value = cutoffs
                    _loading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}