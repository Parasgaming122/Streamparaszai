package com.example.ui.phone

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.HistoryEntry
import com.example.data.model.MediaItem
import com.example.data.repository.MediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _trendingMovies = MutableStateFlow<List<MediaItem>>(emptyList())
    val trendingMovies: StateFlow<List<MediaItem>> = _trendingMovies

    private val _trendingTv = MutableStateFlow<List<MediaItem>>(emptyList())
    val trendingTv: StateFlow<List<MediaItem>> = _trendingTv

    private val _topRated = MutableStateFlow<List<MediaItem>>(emptyList())
    val topRated: StateFlow<List<MediaItem>> = _topRated

    private val _recommended = MutableStateFlow<List<MediaItem>>(emptyList())
    val recommended: StateFlow<List<MediaItem>> = _recommended

    private val _anime = MutableStateFlow<List<MediaItem>>(emptyList())
    val anime: StateFlow<List<MediaItem>> = _anime

    private val _punjabiMovies = MutableStateFlow<List<MediaItem>>(emptyList())
    val punjabiMovies: StateFlow<List<MediaItem>> = _punjabiMovies

    private val _indianMovies = MutableStateFlow<List<MediaItem>>(emptyList())
    val indianMovies: StateFlow<List<MediaItem>> = _indianMovies

    private val _continueWatching = MutableStateFlow<List<HistoryEntry>>(emptyList())
    val continueWatching: StateFlow<List<HistoryEntry>> = _continueWatching

    private val _heroMedia = MutableStateFlow<MediaItem?>(null)
    val heroMedia: StateFlow<MediaItem?> = _heroMedia

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Pre-configure or verify TMDB key
                MediaRepository.configureApi(getApplication())
                
                // Fetch in parallel
                val trendingMoviesDeferred = async { MediaRepository.getTrendingMovies() }
                val trendingTvDeferred = async { MediaRepository.getTrendingTv() }
                val topRatedDeferred = async { MediaRepository.getTopRated() }
                val recommendedDeferred = async { MediaRepository.getRecommended(getApplication()) }
                val continueWatchingDeferred = async { MediaRepository.getContinueWatching(getApplication()) }
                val animeDeferred = async { MediaRepository.getAnime() }
                val punjabiMoviesDeferred = async { MediaRepository.getPunjabiMovies() }
                val indianMoviesDeferred = async { MediaRepository.getIndianMovies() }

                val movies = trendingMoviesDeferred.await()
                _trendingMovies.value = movies
                _trendingTv.value = trendingTvDeferred.await()
                _topRated.value = topRatedDeferred.await()
                _recommended.value = recommendedDeferred.await()
                _continueWatching.value = continueWatchingDeferred.await()
                _anime.value = animeDeferred.await()
                _punjabiMovies.value = punjabiMoviesDeferred.await()
                _indianMovies.value = indianMoviesDeferred.await()

                if (movies.isNotEmpty()) {
                    _heroMedia.value = movies.shuffled().firstOrNull() ?: movies.first()
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to load home page content", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
