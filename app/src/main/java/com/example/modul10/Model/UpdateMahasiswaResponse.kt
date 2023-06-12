package com.example.modul10.Model

import com.google.gson.annotations.SerializedName

data class UpdateMahasiswaResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
