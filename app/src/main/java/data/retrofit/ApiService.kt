
package data.retrofit
import data.response.GithubDetail
import data.response.GithubResponse
import data.response.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListUsers(@Query("q") page: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<GithubDetail>
}