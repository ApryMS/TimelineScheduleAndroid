package deploy.com.timelineschedule.ui

import com.google.gson.annotations.SerializedName

data class  DataUser (
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("position")
    val position: String,

    @field:SerializedName("email")
    val email: String
    )