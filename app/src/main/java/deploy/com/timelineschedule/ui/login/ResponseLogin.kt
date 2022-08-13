package deploy.com.timelineschedule.ui.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("status")
	val status: Boolean,
	@field:SerializedName("message")
	val message: String,
	@field:SerializedName("data")
	val data: Data
)

data class User(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("position")
	val position: String,

	@field:SerializedName("toko")
	val name_toko: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Data(

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
)
