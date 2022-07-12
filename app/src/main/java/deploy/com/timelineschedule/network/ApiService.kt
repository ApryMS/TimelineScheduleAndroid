package deploy.com.timelineschedule.network

import deploy.com.timelineschedule.ui.login.LoginRequest
import deploy.com.timelineschedule.ui.login.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/signin")
    suspend fun login(
        @Body req: LoginRequest
    ) : Response<ResponseLogin>
}