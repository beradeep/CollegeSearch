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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
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
import com.bera.josaahelpertool.use_cases.GetCollegesUseCase
import com.bera.josaahelpertool.utils.CustomDivider
import com.bera.josaahelpertool.utils.Resource
import com.bera.josaahelpertool.utils.ShimmerListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale
import javax.inject.Inject

@Composable
fun CollegeColumn(
    viewModel: CollegeColumnViewModel = hiltViewModel(),
    navController: NavController
) {

    val isLoading = viewModel.loading
    val colleges = viewModel.colleges

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            item {
                CustomDivider(
                    modifier = Modifier.padding(10.dp),
                    text = "${viewModel.category.uppercase()}s",
                    alignment = Alignment.CenterStart
                )
            }
            items(if (isLoading.value) List(20) { "" }
            else colleges.value) {
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
                                navController.navigate(Routes.BranchScreen.route + "/" + it)
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_circled_c),
                            contentDescription = "college",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = it,
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
class CollegeColumnViewModel @Inject constructor(
    private val getCollegesUseCase: GetCollegesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading: MutableState<Boolean> = _loading
    private val _cutoffs = mutableStateOf(Cutoff())
    private val collegeNames = ArrayList<String>()
    private var test = ""
    private val _colleges = mutableStateOf(collegeNames)
    val colleges: State<ArrayList<String>> = _colleges
    lateinit var category: String

    init {
        savedStateHandle.get<String>("category")?.let { category ->
            this.category = category
            getColleges(category)
        }
    }

    private fun getColleges(category: String) {
        getCollegesUseCase(category).onEach { result ->
            when (result) {
                is Resource.Loading -> _loading.value = true
                is Resource.Error -> _loading.value = false
                is Resource.Success -> {
                    _cutoffs.value = result.data as Cutoff
                    _cutoffs.value.forEach { cutoffItem ->
                        if (cutoffItem.Institute != test) {
                            test = cutoffItem.Institute
                            collegeNames.add(test)
                        }
                    }
                    _colleges.value = collegeNames
                    _loading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

}

