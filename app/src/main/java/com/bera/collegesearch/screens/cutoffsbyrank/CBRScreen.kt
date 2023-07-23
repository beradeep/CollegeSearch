package com.bera.collegesearch.screens.cutoffsbyrank

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bera.collegesearch.components.cbr.CBRCutoffs
import com.bera.collegesearch.components.cbr.CBRFilters

@Composable
fun CBRScreen(viewModel: CBRViewModel = hiltViewModel()) {

    val state = viewModel.state

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {

            CBRFilters(
                state = state,
                onAction = viewModel::onAction
            )
            Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp))
            CBRCutoffs(cutoffs = state.cutoffs, isLoading = state.loading)
        }
    }
}