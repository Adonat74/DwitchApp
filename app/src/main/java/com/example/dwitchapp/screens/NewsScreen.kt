package com.example.dwitchapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.dwitchapp.R
import com.example.dwitchapp.model.news.News
import com.example.dwitchapp.service.ApiClient
import com.example.dwitchapp.ui.theme.OpenColors
import kotlinx.coroutines.launch
import timber.log.Timber


class NewsViewModel : ViewModel() {
    private val _news = mutableStateOf<List<News>>(emptyList())
    val news: State<List<News>> = _news

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val token = "Bearer 49b70f996ffbb654be996f8604d118bfca7624ced27749df6f4fdcac30b7009da1ba63ef7d6b91c8ca814baf88955daba2804396ab3b8cd2c03b50a1f96ff330032d2fbc2238338b4f7e25bff9e852b002c26ecca02fbf1e8e261cf6e0cdb00c042e35b33f64dda3522c3178ba1edb22b9daba42b51c1c8355309fd475b5d92b" // Normally you get this token from your auth process

                val response = ApiClient.dwitchService.getNews(token)
                val newsList = response.data // This is List<Order>
                Timber.d("${response}")

                _news.value = newsList

            } catch (e: Exception) {
                Timber.d("Error fetching news: ${e.message}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {
//    var refreshTrigger by remember { mutableIntStateOf(0) } // A state variable to trigger refetch
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(onClick = {
//                            refreshTrigger++
                            viewModel.fetchNews()

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_refresh_24),
                                contentDescription = "Add"
                            )
                        }
                        Text(" Les News")
                    }
                }
            )
        }
    ) { innerPadding ->
        NewsList(
//            refreshTrigger = refreshTrigger,
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}


//view model way
@Composable
fun NewsList(viewModel: NewsViewModel = viewModel(), modifier: Modifier = Modifier) {
    val news by viewModel.news


    LazyColumn (modifier = modifier){

        items(news) { new ->
            NewsItem(new, modifier = Modifier
                .padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
fun NewsItem(new: News, modifier: Modifier) {
    var imageState by remember { mutableStateOf("loading") }


    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(15.dp))
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            AsyncImage(
                model = "https://dwitch.pickle-forge.app${new.medias[0].url}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxSize(),
                onLoading = { loadingState: AsyncImagePainter.State.Loading ->
                    imageState = "loading"
                },
                onSuccess = { successState: AsyncImagePainter.State.Success ->
                    imageState = "success"
                },
                onError = { errorState: AsyncImagePainter.State.Error ->
                    imageState = "error"
                }
            )
            when (imageState) {
                "loading" -> { CircularProgressIndicator(modifier = Modifier.size(50.dp)) }
                "error" -> { Text("Failed to load image") }
                "success" -> {}
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(text = new.title, fontSize = 30.sp, fontWeight = FontWeight.Medium, lineHeight = 38.sp)
                Text(text = new.publishedAt, fontSize = 13.sp)
                Text(text = new.content, fontSize = 18.sp)
            }

        }
    }

}

