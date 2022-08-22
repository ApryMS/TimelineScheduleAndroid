package deploy.com.timelineschedule.ui.registrasi

import com.google.gson.annotations.SerializedName

data class TokoResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class DataItem(

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("nameToko")
	val nameToko: String
)
