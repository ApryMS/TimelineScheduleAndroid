package deploy.com.timelineschedule.ui.home.detailtimeline

import com.google.gson.annotations.SerializedName
import deploy.com.timelineschedule.ui.DataUser

data class TimelineDetailResponse(

	@field:SerializedName("task")
	val task: List<TaskItem>,

	@field:SerializedName("timeline")
	val timeline: Timeline,

	@field:SerializedName("status")
	val status: Boolean
)


data class Timeline(

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
	val makeBy: MakeBy,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusTask")
	val statusTask: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class TaskItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("inviteBy")
	val inviteBy: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("timeline")
	val timeline: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("worker")
	val worker: DataUser,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class MakeBy(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("position")
	val position: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("toko")
	val toko: String
)
