package com.example.modul10.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mahasiswa(
    @SerializedName("nama")
    val nama: String? = null,

    @SerializedName("jurusan")
    val jurusan: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("nrp")
    val nrp: String? = null,

    @SerializedName("email")
    val email: String? = null,
) : Parcelable