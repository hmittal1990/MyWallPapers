package com.android.modal

import com.google.gson.annotations.SerializedName

data class PhotosItem(

	@field:SerializedName("src")
	val src: Src? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("photographer")
	val photographer: String? = null,

	@field:SerializedName("photographer_url")
	val photographerUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)