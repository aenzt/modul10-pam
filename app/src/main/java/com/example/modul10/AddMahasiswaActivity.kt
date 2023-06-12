package com.example.modul10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.modul10.API.APIConfig.apiService
import com.example.modul10.Helper.LoadingState
import com.example.modul10.Model.AddMahasiswaResponse
import com.example.modul10.databinding.ActivityAddMahasiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMahasiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMahasiswaBinding
    private lateinit var loadingState: LoadingState

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadingState = LoadingState(this@AddMahasiswaActivity)

        binding.btnAdd.setOnClickListener {
            loadingState.show()
            addDataMahasiswa()
        }

        binding.btnList.setOnClickListener {
            startActivity(Intent(this@AddMahasiswaActivity, SearchMahasiswaActivity::class.java))
        }
    }

    private fun addDataMahasiswa() {
        val nrp = binding.edtNrp.text
        val nama = binding.edtNama.text
        val email = binding.edtEmail.text
        val jurusan = binding.edtJurusan.text

        if (nrp.isEmpty() || nama.isEmpty() || email.isEmpty() || jurusan.isEmpty()) {
            Toast.makeText(
                this@AddMahasiswaActivity,
                "Silahkan lengkapi form terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
            loadingState.dismiss()
        } else {
            val client = apiService.addMahasiswa(nrp, nama, email, jurusan)
            client!!.enqueue(object : Callback<AddMahasiswaResponse?> {
                override fun onResponse(
                    call: Call<AddMahasiswaResponse?>, response: Response<AddMahasiswaResponse?>
                ) {
                    loadingState.dismiss()
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Toast.makeText(
                                this@AddMahasiswaActivity,
                                "Berhasil menambahakan silahakan cek data pada halaman list!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@AddMahasiswaActivity, HomeActivity::class.java))
                        }
                    } else {
                        if (response.body() != null) {
                            Log.e("", "onFailure: " + response.body()!!.message)
                        }
                    }
                }

                override fun onFailure(call: Call<AddMahasiswaResponse?>, t: Throwable) {
                    loadingState.dismiss()
                    Log.e("Error retrofit", "onFailure: " + t.message)
                }
            })
        }
    }
}