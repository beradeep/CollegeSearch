package com.bera.josaahelpertool.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bera.josaahelpertool.R
import com.bera.josaahelpertool.navigation.Routes
import com.bera.josaahelpertool.ui.theme.rubikFamily
import com.bera.josaahelpertool.utils.ShimmerListItem

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    val searchText by viewModel.searchText.collectAsState()
    val collegeResults by viewModel.collegeResults.collectAsState()
    val branchResults by viewModel.branchResults.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .focusRequester(focusRequester),
                placeholder = { Text(text = "Search colleges and branches") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
            )
            Spacer(modifier = Modifier.height(15.dp))
            LazyColumn {
                if (collegeResults.isEmpty() && branchResults.isEmpty() || loading) {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "No search results.",
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                items(collegeResults) { college ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(60.dp)
                            .clickable {
                                navController.navigate(Routes.BranchScreen.route + "/" + college) {
                                    popUpTo(Routes.HomeScreen.route)
                                }
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
                            text = college,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                items(branchResults) { branch ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(60.dp)
                            .clickable { },
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
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}
