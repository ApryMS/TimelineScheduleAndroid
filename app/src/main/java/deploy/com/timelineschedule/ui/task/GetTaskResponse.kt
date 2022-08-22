package deploy.com.timelineschedule.ui.task

import com.google.gson.annotations.SerializedName

data class GetTaskResponse(

	@field:SerializedName("task")
	val task: List<TaskItem>,

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
	val invite: String,

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

data class InviteBy(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String
)

data class Worker(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String
)

data class TaskItem(

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
	val worker: Worker,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
