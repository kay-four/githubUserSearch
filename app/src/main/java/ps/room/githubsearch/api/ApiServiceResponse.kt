package ps.room.githubsearch.api

import ps.room.githubsearch.data.User

data class ApiServiceResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)