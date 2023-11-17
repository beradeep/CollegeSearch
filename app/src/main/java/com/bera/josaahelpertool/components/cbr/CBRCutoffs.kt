package com.bera.josaahelpertool.components.cbr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bera.josaahelpertool.R
import com.bera.josaahelpertool.models.CutoffItem
import com.bera.josaahelpertool.utils.Constants.FakeCutoffItem
import com.bera.josaahelpertool.utils.CustomDivider
import com.bera.josaahelpertool.utils.ShimmerListItem

@Composable
fun CBRCutoffs(
    cutoffs: ArrayList<CutoffItem>,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(Modifier.fillMaxWidth()) {
            if (!isLoading) {
                item {
                    CustomDivider(
                        text = "Recommended options for you",
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
            items(if (isLoading) List(20) { FakeCutoffItem } else cutoffs) {
                ShimmerListItem(
                    isLoading = isLoading,
                    barCount = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 4.dp, end = 4.dp)
                ) {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp),
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icons8_circled_c),
                                    contentDescription = "college",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = it.Institute,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = TextAlign.Start
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icons8_circled_b),
                                    contentDescription = "branch",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = it.AcademicProgramName,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = TextAlign.Start
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
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
                                    text = buildAnnotatedString {
                                        append("Opening Rank: ")
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(it.OpeningRank)
                                        }
                                    },
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append("Closing Rank: ")
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(it.ClosingRank)
                                        }
                                    },
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            if (!isLoading && cutoffs.isEmpty()) {
                item {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        painter = painterResource(id = R.drawable.no_data),
                        contentDescription = "No data available",
                    )
                }
            }
        }
    }
}
