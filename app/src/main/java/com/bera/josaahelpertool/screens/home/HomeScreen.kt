@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalGlideComposeApi::class
)

package com.bera.josaahelpertool.screens.home

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bera.josaahelpertool.models.ui.TopHalfItem
import com.bera.josaahelpertool.navigation.Routes
import com.bera.josaahelpertool.ui.theme.rubikFamily
import com.bera.josaahelpertool.utils.CustomDivider
import com.bera.josaahelpertool.utils.ShimmerListItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {


    val slideImages by viewModel.slideImages.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Spacer(modifier = Modifier.height(14.dp))
            }
            item {
                Text(
                    text = "JoSAA Made Easy",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                // Create a search bar to type and search for colleges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { navController.navigate(Routes.SearchScreen.route) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Search for colleges, branches, etc.",
                                fontSize = 14.sp,
                                fontFamily = rubikFamily,
                                fontWeight = FontWeight.Thin,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(6.dp)) }
            item {
                FeaturedLinks(Modifier.heightIn(5.dp, 400.dp), viewModel.links)
            }
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }
            item {
                TopHalf(
                    modifier = Modifier
                        .height(380.dp)
                        .padding(6.dp),
                    navController,
                    slideImages,
                    viewModel::changeImagePage,
                )
            }
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }
            item {
                AllCutoffsGrid(
                    modifier = Modifier
                        .heightIn(max = 240.dp)
                        .fillMaxWidth(),
                    navController,
                    viewModel.drawableIds
                )
            }
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }
            item {
                QuoteBox(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    viewModel.quote,
                    viewModel.author,
                    viewModel.isQuoteLoading
                )
            }
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }
            item {
                Text(
                    text = "Made with ♥ by BERA",
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    fontFamily = rubikFamily,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

private var intent = CustomTabsIntent.Builder()
    .build()

@Composable
private fun FeaturedLinks(
    modifier: Modifier = Modifier,
    links: Array<HomeViewModel.Link>
) {
    Column {
        CustomDivider(modifier = Modifier.padding(horizontal = 8.dp), text = "Important Links")
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp)
        ) {
            items(links) {
                val context = LocalContext.current
                Button(
                    onClick = { intent.launchUrl(context, Uri.parse(it.link)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = it.imageRes),
                            contentDescription = it.text,
                            modifier = Modifier
                                .weight(3f)
                                .size(30.dp)
                        )
                        Text(
                            text = it.text,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            fontSize = 10.sp,
                            fontFamily = rubikFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteBox(modifier: Modifier, quote: String, author: String, isLoading: Boolean) {
    Column(modifier = modifier) {
        ShimmerListItem(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            isLoading = isLoading,
            barCount = 2
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CustomDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Quote of the Day"
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                )
                Text(
                    text = "' $quote '",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
                Text(
                    text = "- $author",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive,
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopHalf(
    modifier: Modifier = Modifier,
    navController: NavController,
    topHalfItems: List<TopHalfItem>,
    changeImage: KSuspendFunction2<PagerState, Boolean, Unit>
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        topHalfItems.size
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        if (topHalfItems.isNotEmpty()) {
            HorizontalPager(state = pagerState, key = { topHalfItems[it].imageUrl }) { index ->
                GlideImage(
                    model = topHalfItems[index].imageUrl,
                    contentDescription = topHalfItems[index].toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            FilledTonalIconButton(
                onClick = { scope.launch { changeImage(pagerState, false) } },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .alpha(0.6f)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Image",
                    modifier = modifier.size(24.dp),
                )
            }

            FilledTonalIconButton(
                onClick = { scope.launch { changeImage(pagerState, true) } },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .alpha(0.6f),
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Image",
                    modifier = modifier.size(24.dp),
                )
            }

            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Black.copy(0.6f)
                ),
                onClick = { navController.navigate(Routes.CBRScreen.route) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-60).dp),
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    text = "NEW COLLEGE PREDICTOR",
                    fontFamily = rubikFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF81D5FC)
                )
            }

            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomEnd),
                text = topHalfItems[pagerState.currentPage].name,
                fontSize = 10.sp,
                fontFamily = rubikFamily,
                fontWeight = FontWeight.Thin,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun AllCutoffsGrid(modifier: Modifier, navController: NavController, drawableIds: Array<Int>) {

    val cardText = arrayOf(
        "IIT",
        "NIT",
        "IIIT",
        "GFTI",
    )

    val categoryArr = arrayOf("iit", "nit", "iiit", "ogc")

    Column {
        CustomDivider(modifier = Modifier.padding(horizontal = 8.dp), text = "All Cutoffs")
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 6.dp, top = 2.dp)
        ) {
            items(4, key = { categoryArr[it] }) {
                Button(
                    onClick = { navController.navigate(Routes.CollegeScreen.route + "/" + categoryArr[it]) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = drawableIds[it]),
                            tint = Color.DarkGray.copy(alpha = 0.9f),
                            contentDescription = cardText[it],
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                        Text(
                            text = cardText[it] + "s",
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(2f),
                            fontFamily = rubikFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}