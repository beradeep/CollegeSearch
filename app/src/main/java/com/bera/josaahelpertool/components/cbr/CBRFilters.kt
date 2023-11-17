package com.bera.josaahelpertool.components.cbr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bera.josaahelpertool.screens.cutoffsbyrank.CBRAction
import com.bera.josaahelpertool.screens.cutoffsbyrank.CBRState
import com.bera.josaahelpertool.ui.theme.rubikFamily
import com.bera.josaahelpertool.utils.ShimmerListItem

@Composable
fun CBRFilters(modifier: Modifier = Modifier, state: CBRState, onAction: (CBRAction) -> Unit) {

    var showSortDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(2.dp)
    ) {
        if (state.loading)
            Card(modifier = Modifier.padding(horizontal = 4.dp), shape = RoundedCornerShape(4.dp)) {
                for (i in 1..4) {
                    ShimmerListItem(
                        isLoading = true,
                        barCount = 2,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                    ) { }
                }
            }
        else
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedButton(
                        onClick = { onAction(CBRAction.ExpandFilters) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            val rotationState by animateFloatAsState(
                                targetValue = if (state.expandFilters) 270f else 0f,
                                animationSpec = tween(durationMillis = 300), label = ""
                            )
                            Icon(
                                imageVector = Icons.Rounded.Tune,
                                contentDescription = "Filters",
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                                    .rotate(rotationState)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Filters", modifier = Modifier
                                    .padding(2.dp)
                                    .weight(3f),
                                fontFamily = rubikFamily
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = { showSortDialog = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Sort,
                                contentDescription = "Sort by",
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Sort by:   ${state.sortBy}", modifier = Modifier
                                    .padding(2.dp)
                                    .weight(3f),
                                fontFamily = rubikFamily
                            )
                        }
                    }
                }

                if (showSortDialog) {
                    SortDialogBox(
                        state = state,
                        onAction = onAction,
                        onDismissRequest = { showSortDialog = false },
                    )
                }

                AnimatedVisibility(
                    visible = state.expandFilters
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                        Card(
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)
                        ) {
                            Column(Modifier.padding(4.dp)) {
                                Text(
                                    text = "Rank",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = rubikFamily
                                )
                                val focusManager = LocalFocusManager.current
                                val ignoredRegex = Regex("[.,\\s-]")
                                var rankText by remember {
                                    mutableStateOf(state.rank.toString())
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    value = rankText,
                                    textStyle = TextStyle(fontSize = 14.sp),
                                    onValueChange = {
                                        if (it.length <= 7 && !it.contains(ignoredRegex)) {
                                            rankText = it
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions {
                                        if (rankText == "" || rankText == "0") rankText = "1"
                                        onAction(CBRAction.Rank(rankText.toInt()))
                                        focusManager.clearFocus()
                                    },
                                    singleLine = true
                                )
                            }
                        }
                        Row {
                            Card(
                                modifier = Modifier.weight(1f, fill = true),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Column(Modifier.padding(4.dp)) {
                                    Text(
                                        text = "Exam",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = rubikFamily
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.exam == "Adv",
                                                    onClick = {
                                                        if (state.exam != "Adv") onAction(
                                                            CBRAction.Exam("Adv")
                                                        )
                                                    })
                                                Text(text = "JEE-Adv", fontSize = 10.sp)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.exam == "Main",
                                                    onClick = {
                                                        if (state.exam != "Main") onAction(
                                                            CBRAction.Exam("Main")
                                                        )
                                                    })
                                                Text(text = "JEE-Main", fontSize = 10.sp)
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(1.dp))
                            Card(
                                modifier = Modifier.weight(1f, fill = true),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Column(Modifier.padding(4.dp)) {
                                    Text(
                                        text = "State",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = rubikFamily
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.state == "HS",
                                                    onClick = {
                                                        if (state.state != "HS") onAction(
                                                            CBRAction.State("HS")
                                                        )
                                                    })
                                                Text(text = "HS", fontSize = 10.sp)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.state == "OS",
                                                    onClick = {
                                                        if (state.state != "OS") onAction(
                                                            CBRAction.State("OS")
                                                        )
                                                    })
                                                Text(text = "OS", fontSize = 10.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Row {
                            Card(
                                modifier = Modifier.weight(1f, true),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Column(Modifier.padding(4.dp)) {
                                    Text(
                                        text = "Gender",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = rubikFamily
                                    )
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            RadioButton(selected = state.gender == "Gender-Neutral",
                                                onClick = {
                                                    if (state.gender != "Gender-Neutral") onAction(
                                                        CBRAction.Gender("Gender-Neutral")
                                                    )
                                                })
                                            Text(text = "Gender-Neutral", fontSize = 10.sp)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            RadioButton(selected = state.gender == "Female",
                                                onClick = {
                                                    if (state.gender != "Female") onAction(
                                                        CBRAction.Gender("Female")
                                                    )
                                                })
                                            Text(text = "Female", fontSize = 10.sp)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            RadioButton(selected = state.gender == "Supernumerary",
                                                onClick = {
                                                    if (state.gender != "Supernumerary") onAction(
                                                        CBRAction.Gender("Supernumerary")
                                                    )
                                                })
                                            Text(text = "Other", fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(1.dp))
                            Card(
                                modifier = Modifier.weight(1f, true),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Column(Modifier.padding(4.dp)) {
                                    Text(
                                        text = "PwD",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = rubikFamily
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.pwd,
                                                    onClick = {
                                                        if (!state.pwd) onAction(
                                                            CBRAction.PwD(
                                                                true
                                                            )
                                                        )
                                                    })
                                                Text(text = "Yes", fontSize = 10.sp)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = !state.pwd,
                                                    onClick = {
                                                        if (state.pwd) onAction(
                                                            CBRAction.PwD(
                                                                false
                                                            )
                                                        )
                                                    })
                                                Text(text = "No", fontSize = 10.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Card(
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)
                        ) {
                            Column(Modifier.padding(4.dp)) {
                                Text(
                                    text = "Quota",
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = rubikFamily
                                )
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "OPEN",
                                            onClick = {
                                                if (state.quota != "OPEN") onAction(
                                                    CBRAction.Quota("OPEN")
                                                )
                                            })
                                        Text(text = "OPEN", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "EWS",
                                            onClick = {
                                                if (state.quota != "EWS") onAction(
                                                    CBRAction.Quota(
                                                        "EWS"
                                                    )
                                                )
                                            })
                                        Text(text = "EWS", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "SC",
                                            onClick = {
                                                if (state.quota != "SC") onAction(
                                                    CBRAction.Quota(
                                                        "SC"
                                                    )
                                                )
                                            })
                                        Text(text = "SC", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "ST",
                                            onClick = {
                                                if (state.quota != "ST") onAction(
                                                    CBRAction.Quota(
                                                        "ST"
                                                    )
                                                )
                                            })
                                        Text(text = "ST", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "OBC",
                                            onClick = {
                                                if (state.quota != "OBC") onAction(
                                                    CBRAction.Quota(
                                                        "OBC"
                                                    )
                                                )
                                            })
                                        Text(text = "OBC", fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }


            }
    }
}

@Composable
fun SortDialogBox(state: CBRState, onAction: (CBRAction) -> Unit, onDismissRequest: () -> Unit) {
    var selectedOption by remember { mutableStateOf(state.sortBy) }
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                "Sort By",
                fontFamily = rubikFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == "CR",
                        onClick = { selectedOption = "CR" })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Closing Rank", fontFamily = rubikFamily)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == "OR",
                        onClick = { selectedOption = "OR" })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Opening Rank", fontFamily = rubikFamily)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // Handle confirm option
                onAction(CBRAction.Sort(selectedOption))
                onDismissRequest()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                // Handle dismiss option
                onDismissRequest()
            }) {
                Text("Cancel")
            }
        }
    )
}
