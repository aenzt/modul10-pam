package com.example.modul10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.modul10.API.APIConfig.apiService
import com.example.modul10.Helper.LoadingState
import com.example.modul10.Model.AddMahasiswaResponse
import com.example.modul10.Model.Mahasiswa
import com.example.modul10.Model.UpdateMahasiswaResponse
import com.example.modul10.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var mahasiswa : Mahasiswa
    private lateinit var loadingState: LoadingState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingState = LoadingState(this@DetailActivity)

        mahasiswa = intent.getParcelableExtra(HomeActivity.INTENT_ITEM)!!

        mahasiswa.let {
            binding.edtNrpDetails.setText(mahasiswa.nrp!!)
            binding.edtEmailDetails.setText(mahasiswa.email)
            binding.edtNamaDetails.setText(mahasiswa.nama)
            binding.edtJurusanDetails.setText(mahasiswa.jurusan)
        }

        binding.btnEditDetails.setOnClickListener {
            edit()
        }
    }

    private fun edit(){
        val nrp = binding.edtNrpDetails.text
        val email = binding.edtEmailDetails.text
        val jurusan = binding.edtJurusanDetails.text
        val nama = binding.edtNamaDetails.text
        val id = mahasiswa.id

        loadingState.show()

        if(nrp.isEmpty() || email.isEmpty() || jurusan.isEmpty() || nama.isEmpty()){
            loadingState.dismiss()
            Toast.makeText(
                this@DetailActivity,
                "Silahkan lengkapi form terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
        }else {
            val client = apiService.updateMahasiswa(id, nrp, nama, email, jurusan)
            client!!.enqueue(object : Callback<UpdateMahasiswaResponse?> {
                override fun onResponse(
                    call: Call<UpdateMahasiswaResponse?>,
                    response: Response<UpdateMahasiswaResponse?>
                ) {
                    loadingState.dismiss()
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Toast.makeText(
                                this@DetailActivity,
                                "Berhasil mengubah data",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@DetailActivity, HomeActivity::class.java))
                        }
                    } else {
                        loadingState.dismiss()
                        if (response.body() != null) {
                            Log.e("", "onFailure: " + response.body()!!.message)
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateMahasiswaResponse?>, t: Throwable) {
                    loadingState.dismiss()
                    Log.e("Error retrofit", "onFailure: " + t.message)
                }

            })
        }
    }
}