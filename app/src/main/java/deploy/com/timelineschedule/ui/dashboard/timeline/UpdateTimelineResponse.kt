package deploy.com.timelineschedule.ui.dashboard.timeline

import com.google.gson.annotations.SerializedName

data class UpdateTimelineResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean
)
