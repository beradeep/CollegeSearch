package com.bera.collegesearch.components.cbr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import com.bera.collegesearch.screens.cutoffsbyrank.CBRAction
import com.bera.collegesearch.screens.cutoffsbyrank.CBRState
import com.bera.collegesearch.utils.ShimmerListItem

@Composable
fun CBRFilters(modifier: Modifier = Modifier, state: CBRState, onAction: (CBRAction) -> Unit) {

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
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedButton(
                        onClick = { onAction(CBRAction.ExpandFilters) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Filters", modifier = Modifier
                                    .padding(2.dp)
                                    .weight(3f)
                            )
                            val rotationState by animateFloatAsState(
                                targetValue = if (state.expandFilters) 270f else 0f,
                                animationSpec = tween(durationMillis = 300)
                            )
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Filters",
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                                    .rotate(rotationState)
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = { onAction(CBRAction.Sort(if (state.sortBy == "CR") "OR" else "CR")) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Sort by:   ${state.sortBy}", modifier = Modifier
                                    .padding(2.dp)
                                    .weight(3f)
                            )
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Sort by",
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = state.expandFilters,
                    enter = expandVertically(tween(durationMillis = 300, easing = LinearEasing)),
                    exit = shrinkVertically(tween(durationMillis = 300, easing = LinearEasing))
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                        Card(
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)
                        ) {
                            Column(Modifier.padding(4.dp)) {
                                Text(text = "Rank", fontWeight = FontWeight.Bold)
                                val focusManager = LocalFocusManager.current
                                val ignoredRegex = Regex("[.,\\s-]")
                                var rankText by remember {
                                    mutableStateOf(state.rank.toString())
                                }
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
                                    Text(text = "Exam", fontWeight = FontWeight.Bold)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.exam == "Adv",
                                                    enabled = state.exam != "Adv",
                                                    onClick = { onAction(CBRAction.Exam("Adv")) })
                                                Text(text = "JEE-Adv", fontSize = 10.sp)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.exam == "Main",
                                                    enabled = state.exam != "Main",
                                                    onClick = { onAction(CBRAction.Exam("Main")) })
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
                                    Text(text = "State", fontWeight = FontWeight.Bold)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.state == "HS",
                                                    enabled = state.state != "HS",
                                                    onClick = { onAction(CBRAction.State("HS")) })
                                                Text(text = "HS", fontSize = 10.sp)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.state == "OS",
                                                    enabled = state.state != "OS",
                                                    onClick = { onAction(CBRAction.State("OS")) })
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
                                    Text(text = "Gender", fontWeight = FontWeight.Bold)
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            RadioButton(selected = state.gender == "Gender-Neutral",
                                                enabled = state.gender != "Gender-Neutral",
                                                onClick = { onAction(CBRAction.Gender("Gender-Neutral")) })
                                            Text(text = "Gender-Neutral", fontSize = 10.sp)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            RadioButton(selected = state.gender == "Female",
                                                enabled = state.gender != "Female",
                                                onClick = { onAction(CBRAction.Gender("Female")) })
                                            Text(text = "Female", fontSize = 10.sp)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            RadioButton(selected = state.gender == "Supernumerary",
                                                enabled = state.gender != "Supernumerary",
                                                onClick = { onAction(CBRAction.Gender("Supernumerary")) })
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
                                    Text(text = "PwD", fontWeight = FontWeight.Bold)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = state.pwd,
                                                    enabled = !state.pwd,
                                                    onClick = { onAction(CBRAction.PwD(true)) })
                                                Text(text = "Yes", fontSize = 10.sp)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                RadioButton(selected = !state.pwd,
                                                    enabled = state.pwd,
                                                    onClick = { onAction(CBRAction.PwD(false)) })
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
                                Text(text = "Quota", fontWeight = FontWeight.Bold)
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "OPEN",
                                            enabled = state.quota != "OPEN",
                                            onClick = { onAction(CBRAction.Quota("OPEN")) })
                                        Text(text = "OPEN", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "EWS",
                                            enabled = state.quota != "EWS",
                                            onClick = { onAction(CBRAction.Quota("EWS")) })
                                        Text(text = "EWS", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "SC",
                                            enabled = state.quota != "SC",
                                            onClick = { onAction(CBRAction.Quota("SC")) })
                                        Text(text = "SC", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "ST",
                                            enabled = state.quota != "ST",
                                            onClick = { onAction(CBRAction.Quota("ST")) })
                                        Text(text = "ST", fontSize = 10.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        RadioButton(selected = state.quota == "OBC",
                                            enabled = state.quota != "OBC",
                                            onClick = { onAction(CBRAction.Quota("OBC")) })
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