package deploy.com.timelineschedule.network

import deploy.com.timelineschedule.ui.DataUser
import deploy.com.timelineschedule.ui.dashboard.diskusi.ResponseGetListDiskusi
import deploy.com.timelineschedule.ui.dashboard.diskusi.detail.ResponseDetailDiskusi
import deploy.com.timelineschedule.ui.dashboard.timeline.TimelineResponse
import deploy.com.timelineschedule.ui.dashboard.timeline.UpdateTimelineResponse
import deploy.com.timelineschedule.ui.home.changeinvitation.AddTaskResponse
import deploy.com.timelineschedule.ui.home.addtimeline.AddTimelineResponse
import deploy.com.timelineschedule.ui.home.addtimeline.InviteResponse
import deploy.com.timelineschedule.ui.home.detailtimeline.TimelineDetailResponse
import deploy.com.timelineschedule.ui.login.LoginRequest
import deploy.com.timelineschedule.ui.login.ResponseLogin
import deploy.com.timelineschedule.ui.registrasi.TokoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("diskusi/detail-diskusi")
    suspend fun getDetailDiskusi(
        @Query("idDiskusi") idDiskusi : String,
        @Header("Authorization") token : String
    ) : Response<ResponseDetailDiskusi>


    @GET("timeline/detail-timeline")
    suspend fun getDetailTimeline(
        @Query("id") id : String,
        @Header("Authorization") token : String
    ) : Response<TimelineDetailResponse>


    @FormUrlEncoded
    @POST("employee/all")
    suspend fun getEmployee(@Field("idUser") idUser: String) : Response<InviteResponse>

    @FormUrlEncoded
    @POST("employee/all-worker")
    suspend fun getEmployeeWorker(@Field("idUserInvite") idUserInvite: String) : Response<InviteResponse>

    @GET("employee/by-name")
    suspend fun getEmployeeByName(
        @Query("name") name: String
    ) : Response<DataUser>

    @Multipart
    @POST("timeline/add-timeline")
    suspend fun postTimeline(
        @Header("Authorization") token: String,
        @Part file : MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("makeBy") makeBy: RequestBody,
        @Part("description") description: RequestBody,
        @Part("invite") invite: RequestBody,

    ) : Response<AddTimelineResponse>


    @GET("timeline/list-toko")
    suspend fun  getListToko() : Response<TokoResponse>


    @FormUrlEncoded
    @PUT("timeline/edit-status-task")
    suspend fun updateStatusTaskTimeline(
        @Field("idTimeline") idTimeline: String,
        @Header("Authorization") token: String
    ) : Response<UpdateTimelineResponse>

    @FormUrlEncoded
    @PUT("timeline/edit-status")
    suspend fun updateTimeline(
        @Field("idTimeline") idTimeline: String,
        @Header("Authorization") token: String
    ) : Response<UpdateTimelineResponse>

    @FormUrlEncoded
    @PUT("timeline/edit-invitation")
    suspend fun changeInvite(
        @Field("idTimeline") idTimeline: String,
        @Field("idInvite") idInvite: String,
        @Header("Authorization") token: String
    ) : Response<UpdateTimelineResponse>

    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun register(
        @Field("name") name :String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("toko") nameToko: String
    ) : Response<ResponseLogin>

    @FormUrlEncoded
    @POST("diskusi/add-diskusi")
    suspend fun addDiskusi(
        @Field("note") note :String,
        @Field("timeline") idTimeline: String,
        @Field("makeBy") makeBy: String,
        @Field("invite") invite: String,
        @Header("Authorization") token: String
    ) : Response<AddTimelineResponse>

    @FormUrlEncoded
    @POST("diskusi/komentar-diskusi")
    suspend fun komentarDiskusi(
        @Field("diskusiId") note :String,
        @Field("userId") idTimeline: String,
        @Field("komen") makeBy: String,
        @Header("Authorization") token: String
    ) : Response<AddTimelineResponse>


    @GET("diskusi/get-diskusi-by-iduser")
    suspend fun getDiskusiByUser(
        @Query("idUser") idUser : String,
        @Header("Authorization") token : String
    ) : Response<ResponseGetListDiskusi>

}