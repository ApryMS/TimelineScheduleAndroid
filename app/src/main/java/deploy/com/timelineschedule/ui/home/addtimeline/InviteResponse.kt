package deploy.com.timelineschedule.ui.home.addtimeline

import com.google.gson.annotations.SerializedName

data class InviteResponse(

	@field:SerializedName("data")
	val data: List<EmployeeInvite>,

	@field:SerializedName("status")
	val status: Boolean
)

data class EmployeeInvite(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String
)
