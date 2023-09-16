package data.retrofit
import data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*


    interface ApiService {
        @GET("search/users")
        fun getListUsers(@Query("q") page: String): Call<List<GithubResponse>>
    }
