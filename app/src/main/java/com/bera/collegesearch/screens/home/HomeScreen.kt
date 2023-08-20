package com.bera.collegesearch.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bera.collegesearch.navigation.Routes
import com.bera.collegesearch.ui.theme.CollegeSearchTheme
import com.bera.collegesearch.utils.CustomOutlinedCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopHalf(
                modifier = Modifier
                    .weight(10f)
                    .padding(6.dp),
                navController,
                viewModel.slideImage,
                viewModel.pagerState,
                viewModel::changeImagePage
            )
            MainGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                navController,
                viewModel.drawableIds
            )
            QuoteBox(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                viewModel.quote,
                viewModel.author
            )
            Text(
                text = "Made with ♥ by BERA",
                modifier = Modifier.weight(0.6f),
                textAlign = TextAlign.Center,
                fontSize = 11.sp
            )
        }

    }

}

@Composable
fun QuoteBox(modifier: Modifier, quote: String, author: String) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "Quote of the day: ",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )
            Text(
                text = "\" $quote \"",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify,
                fontStyle = FontStyle.Italic
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )
            Text(text = "- $author", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopHalf(
    modifier: Modifier = Modifier,
    navController: NavController,
    slideImage: Array<Int>,
    pagerState: PagerState,
    changeImage: suspend (Boolean) -> Unit
) {

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        HorizontalPager(state = pagerState, pageCount = 4, key = { slideImage[it] }) { index ->
            Image(
                painter = painterResource(id = slideImage[index]),
                contentDescription = "IIT Bombay",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = { scope.launch { changeImage(false) } },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Image",
                modifier = modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = { scope.launch { changeImage(true) } },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Image",
                modifier = modifier.size(40.dp),
                MaterialTheme.colorScheme.primary
            )
        }

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp),
            onClick = { navController.navigate(Routes.CBRScreen.route) }) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Search for colleges by Rank",
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun MainGrid(modifier: Modifier, navController: NavController, drawableIds: Array<Int>) {

    val cardText = arrayOf(
        "IIT",
        "NIT",
        "IIIT",
        "GFTI",
    )

    val categoryArr = arrayOf("iit", "nit", "iiit", "ogc")

    CustomOutlinedCard(modifier = modifier, label = "JoSAA Cutoffs") {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(4, key = { categoryArr[it] }) {
                Button(
                    onClick = { navController.navigate(Routes.CollegeScreen.route + "/" + categoryArr[it]) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 4.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = drawableIds[it]),
                            contentDescription = cardText[it],
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(2f)
                        )
                        Text(
                            text = cardText[it],
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(2f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    CollegeSearchTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}