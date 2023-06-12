package com.example.modul10.API

import android.text.Editable
import com.example.modul10.Model.AddMahasiswaResponse
import com.example.modul10.Model.DeleteMahasiswaResponse
import com.example.modul10.Model.MahasiswaResponse
import com.example.modul10.Model.UpdateMahasiswaResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("mahasiswa")
    fun getMahasiswaByNrp(@Query("nrp") nrp: Editable): Call<MahasiswaResponse>

    @GET("mahasiswa")
    fun getAllMahasiswa(): Call<MahasiswaResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "mahasiswa", hasBody = true)
    fun deleteMahasiswa (
        @Field("id") id: String?,
    ) : Call<DeleteMahasiswaResponse>

    @FormUrlEncoded
    @PUT("mahasiswa")
    fun updateMahasiswa (
        @Field("id") id: String?,
        @Field("nrp") nrp: Editable,
        @Field("nama") nama: Editable,
        @Field("email") email: Editable,
        @Field("jurusan") jurusan: Editable
    ) : Call<UpdateMahasiswaResponse>

    @POST("mahasiswa")
    @FormUrlEncoded
    fun addMahasiswa(
        @Field("nrp") nrp: Editable,
        @Field("nama") nama: Editable,
        @Field("email") email: Editable,
        @Field("jurusan") jurusan: Editable
    ): Call<AddMahasiswaResponse?>?
}