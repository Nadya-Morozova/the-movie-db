package com.example.themovies.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.themovies.data.RecordType

class TheMovieDBPagingSource (
    private val loadData: suspend (page: Int) -> List<RecordType>
) : PagingSource<Int, RecordType>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordType> {
        val nextPageNumber = params.key ?: 1

        return try {
            val list = loadData(nextPageNumber)
            val nextKey =
                if (list.isEmpty()) null
                else nextPageNumber + 1

            LoadResult.Page(
                data = list,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Log.e("MoviesPagingSource", exception.message ?: "")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecordType>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}