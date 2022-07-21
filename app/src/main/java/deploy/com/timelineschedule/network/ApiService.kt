package deploy.com.timelineschedule.network

import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.home.TimelineResponse
import deploy.com.timelineschedule.ui.home.addtimeline.AddTimelineResponse
import deploy.com.timelineschedule.ui.home.addtimeline.InviteResponse
import deploy.com.timelineschedule.ui.home.detailtimeline.TimelineDetailResponse
import deploy.com.timelineschedule.ui.login.LoginRequest
import deploy.com.timelineschedule.ui.login.ResponseLogin
import deploy.com.timelineschedule.ui.task.GetTaskResponse
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

    @GET("timeline/timeline-invite-status")
    suspend fun getTimelineInviteWithStatus(
        @Query("invite") invite : String,
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

    @FormUrlEncoded
    @POST("timeline/add-timeline")
    suspend fun postTimeline(
        @Field("name") name: String,
        @Field("makeBy") makeBy: String,
        @Field("description") description: String,
        @Field("invite") invite: String,
        @Header("Authorization") token : String
    ) : Response<AddTimelineResponse>

    @GET("task/show-task-status")
    suspend fun getTaskByUserAndStatus(
        @Query("userId") userId : String,
        @Query("status") status : String,
        @Header("Authorization") token: String
    ) : Response<GetTaskResponse>

}