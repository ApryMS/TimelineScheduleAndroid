package deploy.com.timelineschedule.network

import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.home.TimelineResponse
import deploy.com.timelineschedule.ui.home.addtimeline.InviteResponse
import deploy.com.timelineschedule.ui.home.detailtimeline.TimelineDetailResponse
import deploy.com.timelineschedule.ui.login.LoginRequest
import deploy.com.timelineschedule.ui.login.ResponseLogin
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/signin")
    suspend fun login(
        @Body req: LoginRequest
    ) : Response<ResponseLogin>

    @GET("timeline/my-timeline-status")
    suspend fun getTimelineWithStatus(
        @Query("idUser") idUser : String,
        @Query("status") status : String,
        @Header("Authorization") token : String
    ) : Response<TimelineResponse>

    @GET("timeline/detail-timeline")
    suspend fun getDetailTimeline(
        @Query("id") id : String,
        @Header("Authorization") token : String
    ) : Response<TimelineDetailResponse>

    @GET("employee/all")
    suspend fun getEmployee() : Response<InviteResponse>

    @GET("employee/by-name")
    suspend fun getEmployeeByName(
        @Query("name") name: String
    ) : Response<DataUser>
}