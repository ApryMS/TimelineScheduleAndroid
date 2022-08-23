package deploy.com.timelineschedule.ui.home.changeinvitation

import com.google.gson.annotations.SerializedName

data class AddTaskResponse(

	@field:SerializedName("task")
	val task: Task,

	@field:SerializedName("status")
	val status: Boolean
)

data class Task(

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
	val worker: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
