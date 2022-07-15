package deploy.com.timelineschedule.ui.home

import com.google.gson.annotations.SerializedName

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
	val invite: Invite,

	@field:SerializedName("makeBy")
	val makeBy: MakeBy,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class MakeBy(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String
)

data class Invite(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String
)


