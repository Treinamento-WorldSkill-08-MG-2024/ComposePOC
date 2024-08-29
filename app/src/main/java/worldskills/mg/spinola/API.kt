package worldskills.mg.spinola

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
    @GET("test/{id}")
    suspend fun getExample(@Path("id") id: String): Response<ResponseBody>
}
