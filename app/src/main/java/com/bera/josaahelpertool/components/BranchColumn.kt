package com.bera.josaahelpertool.components

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bera.josaahelpertool.R
import com.bera.josaahelpertool.models.Cutoff
import com.bera.josaahelpertool.navigation.Routes
import com.bera.josaahelpertool.use_cases.GetBranchesUseCase
import com.bera.josaahelpertool.utils.CustomDivider
import com.bera.josaahelpertool.utils.Resource
import com.bera.josaahelpertool.utils.ShimmerListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@Composable
fun BranchColumn(
    collegeName: String,
    navController: NavController,
    viewModel: BranchColumnViewModel = hiltViewModel()
) {

    val isLoading = viewModel.loading
    val branches = viewModel.branches

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            item {
                CustomDivider(
                    modifier = Modifier.padding(10.dp),
                    text = collegeName.uppercase(),
                    alignment = Alignment.CenterStart
                )
            }
            items(if (isLoading.value) List(20) { "" }
            else branches.value) { branch ->
                ShimmerListItem(
                    isLoading = isLoading.value,
                    barCount = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 4.dp, end = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp)
                            .clickable {
                                navController.navigate(Routes.CutoffScreen.route + "/$collegeName/$branch")
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_circled_b),
                            contentDescription = "branch",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = branch,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}

@HiltViewModel
class BranchColumnViewModel @Inject constructor(
    private val getBranchesUseCase: GetBranchesUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _loading = mutableStateOf(true)
    val loading: State<Boolean> = _loading
    private val _cutoffs = mutableStateOf(Cutoff())
    private val branchNames = ArrayList<String>()
    private var test = ""
    private val _branches = mutableStateOf(branchNames)
    val branches: State<ArrayList<String>> = _branches

    init {
        savedStateHandle.get<String>("college")?.let { college ->
            getBranches(college)
        }
    }

    private fun getBranches(college: String) {
        getBranchesUseCase(college).onEach { result ->
            when (result) {
                is Resource.Error -> _loading.value = false
                is Resource.Loading -> _loading.value = true
                is Resource.Success -> {
                    _cutoffs.value = result.data as Cutoff
                    _cutoffs.value.forEach { cutoffItem ->
                        if (cutoffItem.AcademicProgramName != test) {
                            test = cutoffItem.AcademicProgramName
                            branchNames.add(test)
                        }
                    }
                    _branches.value = branchNames
                    _loading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}



