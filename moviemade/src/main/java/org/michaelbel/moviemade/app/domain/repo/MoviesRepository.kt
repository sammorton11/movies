package org.michaelbel.moviemade.app.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.Movie
import org.michaelbel.moviemade.app.data.model.MovieResponse
import org.michaelbel.moviemade.app.data.model.Result
import org.michaelbel.moviemade.app.domain.data.MoviesPagingSource
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: Api
) {

    suspend fun movies(
        list: String,
        apiKey: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        return api.movies(list, apiKey, language, page)
    }

    fun moviesStream(
        list: String,
        apiKey: String,
        language: String
    ): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = MOVIES_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(api, list, apiKey, language) }
        ).flow
    }

    suspend fun movie(
        movieId: Long,
        apiKey: String,
        language: String
    ): Response<Movie> = api.movie(movieId, apiKey, language)

    suspend fun movie(
        movieId: Long,
        apiKey: String,
        language: String,
        addToResponse: String
    ): Response<Movie> = api.movie(movieId, apiKey, language, addToResponse)

    companion object {
        const val MOVIES_PAGE_SIZE = 20
    }
}