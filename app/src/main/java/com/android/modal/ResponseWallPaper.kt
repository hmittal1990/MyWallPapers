package com.android.modal

import com.google.gson.annotations.SerializedName

data class ResponseWallPaper(

	@field:SerializedName("next_page")
	val nextPage: String? = null,

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("photos")
	val photos: ArrayList<PhotosItem>? = null
)