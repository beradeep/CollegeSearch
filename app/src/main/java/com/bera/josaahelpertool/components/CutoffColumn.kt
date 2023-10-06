package com.bera.josaahelpertool.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bera.josaahelpertool.models.Cutoff
import com.bera.josaahelpertool.use_cases.GetCutoffsUseCase
import com.bera.josaahelpertool.utils.Constants
import com.bera.josaahelpertool.utils.Resource
import com.bera.josaahelpertool.utils.ShimmerListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@Composable
fun CutoffColumn(
    viewModel: CutoffColumnViewModel = hiltViewModel(),
) {
    val isLoading = viewModel.loading
    val cutoffs = viewModel.finalCutoffs

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .padding(10.dp, 5.dp)
                .fillMaxSize()) {
            items(if (isLoading.value) List(20) { Constants.FakeCutoffItem }
            else cutoffs.value) {
                ShimmerListItem(
                    isLoading = isLoading.value,
                    barCount = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 4.dp, end = 4.dp)
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 6.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = it.Institute)
                            Text(text = it.AcademicProgramName)
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = it.Quota, color = MaterialTheme.colorScheme.primary)
                                Text(
                                    text = it.Gender.removeSuffix("(including Supernumerary)"),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(text = it.SeatType, color = MaterialTheme.colorScheme.primary)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "OR: ${it.OpeningRank}",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "CR: ${it.ClosingRank}",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }

//    if (!isLoading.value) {
//        Surface(modifier = Modifier.fillMaxSize(),color = Color.White) {
//            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
//                items(cutoffs.value) {
//                    Card(
//                        modifier = Modifier
//                            .height(200.dp)
//                            .fillMaxWidth()
//                            .padding(vertical = 5.dp),
//                        colors = CardDefaults.cardColors(containerColor = BlueShade1),
//                        elevation = CardDefaults.cardElevation(2.dp)
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 10.dp),
//                            verticalArrangement = Arrangement.SpaceEvenly
//                        ) {
//                            Text(text = it.AcademicProgramName)
//                            Row(
//                                Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Text(text = it.Quota)
//                                Text(text = it.Gender.removeSuffix("(including Supernumerary)"))
//                                Text(text = it.SeatType)
//                            }
//                            Row(
//                                Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Text(
//                                    text = "OR: ${it.OpeningRank}",
//                                    fontFamily = FontFamily.Monospace,
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                                Text(
//                                    text = "CR: ${it.ClosingRank}",
//                                    fontFamily = FontFamily.Monospace,
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    } else {
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            LinearProgressIndicator(color = BlueShade2, trackColor = BlueShade1)
//        }
//    }

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

    init {
        getCutoffs(
            savedStateHandle.get<String>("college")!!,
            savedStateHandle.get<String>("branch")!!
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