package com.example.modul10.Model

import com.google.gson.annotations.SerializedName

data class DeleteMahasiswaResponse(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
