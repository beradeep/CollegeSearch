package com.bera.collegesearch.components.cbr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bera.collegesearch.models.CutoffItem
import com.bera.collegesearch.utils.Constants.FakeCutoffItem
import com.bera.collegesearch.utils.ShimmerListItem

@Composable
fun CBRCutoffs(
    cutoffs: ArrayList<CutoffItem>,
    isLoading: Boolean
) {
    Box(modifier = Modifier.fillMaxSize().padding(2.dp), contentAlignment = Alignment.Center) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(if (isLoading) List(20) { FakeCutoffItem }
            else cutoffs) {
                ShimmerListItem(
                    isLoading = isLoading,
                    barCount = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 4.dp, end = 4.dp)
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 6.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = it.Institute, fontWeight = FontWeight.Bold)
                            Text(
                                text = it.AcademicProgramName,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = it.Quota)
                                Text(text = it.Gender.removeSuffix("(including Supernumerary)"))
                                Text(text = it.SeatType)
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
//        if (!isLoading) {
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                items(cutoffs) {
//                    Card(
//                        modifier = Modifier
//                            .height(200.dp)
//                            .fillMaxWidth()
//                            .padding(bottom = 5.dp),
//                        colors = CardDefaults.cardColors(containerColor = BlueShade1),
//                        elevation = CardDefaults.cardElevation(2.dp)
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 6.dp),
//                            verticalArrangement = Arrangement.SpaceEvenly
//                        ) {
//                            Text(text = it.Institute)
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
    }
}
