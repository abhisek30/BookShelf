package com.abhisek.project.bookshelf.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.abhisek.project.bookshelf.domain.models.Book
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigateToAuthentication: () -> Unit,
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is DashboardEffect.NavigateToAuthentication -> onNavigateToAuthentication()
            }
        }.collect()
    }

    uiState.value.books?.let { books ->
        Column(
            modifier = modifier, verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val years = books.map { it.key }
            val pagerState = rememberPagerState(pageCount = years.count(), initialPage = 0)
            val coroutineScope = rememberCoroutineScope()
            Column(
                modifier = Modifier
                    .fillMaxSize(), verticalArrangement = Arrangement.Top
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "BookShelf",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                    },
                    actions = {
                        Text(
                            text = "Logout", modifier = Modifier
                                .clickable {
                                    viewModel.logout()
                                }
                                .padding(horizontal = 12.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    backgroundColor = Color.Gray,
                    contentColor = Color.White
                )
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.White,
                    contentColor = Color.Gray,
                    divider = {
                        Spacer(modifier = Modifier.height(5.dp))
                    },
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier =
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)),
                            height = 5.dp,
                            color = Color.Gray,
                        )
                    },
                    modifier = Modifier,
                ) {
                    years.forEachIndexed { index, tabTitle ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = tabTitle.toString(),
                                    style = TextStyle.Default,
                                    color = Color.Black,
                                )
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.White,
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.Top,
                    dragEnabled = true,
                ) { index ->
                    LazyColumn {
                        items(books[years[index]]?.size ?: 0) {
                            IndividualBookDetailCard(books[years[index]]?.get(it))
                        }
                    }
                }
            }
        }
    } ?: run {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun IndividualBookDetailCard(book: Book?) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                isFavorite = !isFavorite
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = book?.image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book?.title.orEmpty(),
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Score: ${book?.score ?: ""}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }

            IconButton(onClick = {
                isFavorite = !isFavorite
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    BookShelfTheme {
        DashboardScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            {}
        )
    }
}