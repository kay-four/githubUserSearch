package ps.room.githubsearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ps.room.githubsearch.api.GithubService
import ps.room.githubsearch.data.User

class GithubRepository {

    private val githubService= GithubService()

    suspend fun getUserLogins(query:String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(githubService, query) }
        ).flow
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

}
