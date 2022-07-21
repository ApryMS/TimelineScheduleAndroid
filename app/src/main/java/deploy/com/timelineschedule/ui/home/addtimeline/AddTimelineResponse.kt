package deploy.com.timelineschedule.ui.home.addtimeline

import com.google.gson.annotations.SerializedName

data class AddTimelineResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)
