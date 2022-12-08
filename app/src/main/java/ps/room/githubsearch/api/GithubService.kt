package ps.room.githubsearch.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/search/"
const val IN_QUALIFIER = "in:login"
interface GithubService {
    @GET("users")
    suspend fun getUserLogins(@Query("q") query:String
    ) : Response<ApiServiceResponse>

    companion object {
        operator fun invoke() : GithubService{

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return  Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(GithubService::class.java)

        }
    }
}
