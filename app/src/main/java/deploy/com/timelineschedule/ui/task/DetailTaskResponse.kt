package deploy.com.timelineschedule.ui.task

import com.google.gson.annotations.SerializedName

data class DetailTaskResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)


data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("inviteBy")
	val inviteBy: InviteBy,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("timeline")
	val timeline: Timeline,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("worker")
	val worker: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)


