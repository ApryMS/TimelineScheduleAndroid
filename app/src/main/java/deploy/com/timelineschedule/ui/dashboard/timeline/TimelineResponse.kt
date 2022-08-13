package deploy.com.timelineschedule.ui.dashboard.timeline

import com.google.gson.annotations.SerializedName
import deploy.com.timelineschedule.ui.DataUser

data class TimelineResponse(

	@field:SerializedName("timeline")
	val timeline: List<TimelineItem>,

	@field:SerializedName("status")
	val status: Boolean
)
data class TimelineItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("invite")
	val invite: DataUser,

	@field:SerializedName("makeBy")
	val makeBy: DataUser,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)




