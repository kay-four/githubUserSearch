package ps.room.githubsearch.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ps.room.githubsearch.api.GithubService
import ps.room.githubsearch.api.IN_QUALIFIER
import ps.room.githubsearch.data.User
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class UserPagingSource(
    private val apiService: GithubService,
    private val query: String
):PagingSource<Int,User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, User> {

        val apiQuery = query+ IN_QUALIFIER
        val position = params.key?:GITHUB_STARTING_PAGE_INDEX

        return try {

            val response = apiService.getUserLogins(apiQuery)
            val users = if(response.isSuccessful) { response.body()!!.items}
            else emptyList()

            PagingSource.LoadResult.Page(
                data = users,
                prevKey = if(position == GITHUB_STARTING_PAGE_INDEX) null else position.minus(1),
                nextKey = position.plus(1)
            )

        } catch (exception: IOException) {
            return PagingSource.LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return PagingSource.LoadResult.Error(exception)
        }
    }
    override val keyReuseSupported: Boolean = true


}