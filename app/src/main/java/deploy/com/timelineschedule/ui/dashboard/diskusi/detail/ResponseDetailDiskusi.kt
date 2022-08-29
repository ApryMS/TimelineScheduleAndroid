package deploy.com.timelineschedule.ui.dashboard.diskusi.detail

import com.google.gson.annotations.SerializedName

data class ResponseDetailDiskusi(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("diskusi")
	val diskusi: Diskusi,

	@field:SerializedName("status")
	val status: Boolean
)

data class Timeline(

	@field:SerializedName("image")
	val image: String,

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

	@field:SerializedName("statusTask")
	val statusTask: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Diskusi(

	@field:SerializedName("note")
	val note: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("timeline")
	val timeline: Timeline,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("invite")
	val invite: Invite,

	@field:SerializedName("makeBy")
	val makeBy: MakeBy,

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
