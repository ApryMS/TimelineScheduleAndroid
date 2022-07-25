package deploy.com.timelineschedule.ui.task

import com.google.gson.annotations.SerializedName

data class UpdateTaskResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)
