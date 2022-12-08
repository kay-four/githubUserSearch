package ps.room.githubsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ps.room.githubsearch.api.GithubService
import ps.room.githubsearch.data.User
import ps.room.githubsearch.repository.GithubRepository
import ps.room.githubsearch.repository.UserPagingSource

class SearchViewModel(private val repository: GithubRepository)
    : ViewModel() {

    private val apiService = GithubService()
    lateinit var userPagingData : Flow<PagingData<User>>




    suspend fun fetchUserLogins(login: String) : Flow<PagingData<User>> {
        repository.getUserLogins(login)
        userPagingData = Pager(
            PagingConfig(
                pageSize = 9
            )
        ){
            UserPagingSource(apiService,login )
        }.flow
            .cachedIn(viewModelScope)
        return userPagingData
    }
}

class SearchViewModelFactory(
    private val repository: GithubRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}
