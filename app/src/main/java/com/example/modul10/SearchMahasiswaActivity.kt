package com.example.modul10

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.modul10.API.APIConfig.apiService
import com.example.modul10.Helper.LoadingState
import com.example.modul10.Model.Mahasiswa
import com.example.modul10.Model.MahasiswaResponse
import com.example.modul10.databinding.ActivitySearchMahasiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMahasiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMahasiswaBinding
    private var mahasiswaList: List<Mahasiswa>? = null
    private lateinit var loadingState : LoadingState

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchMahasiswaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadingState = LoadingState(this@SearchMahasiswaActivity)

        mahasiswaList = ArrayList()

        binding.btnSearch.setOnClickListener {
            loadingState.show()
            val nrp = binding.edtChckNrp.text
            if (nrp.isEmpty()) {
                binding.edtChckNrp.error = "Silahkan isi NRP terlebih dahulu"
                loadingState.dismiss()
            } else {
                val client = apiService.getMahasiswaByNrp(nrp)
                client.enqueue(object : Callback<MahasiswaResponse?> {
                    override fun onResponse(
                        call: Call<MahasiswaResponse?>,
                        response: Response<MahasiswaResponse?>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                loadingState.dismiss()
                                mahasiswaList = response.body()!!.data
                                setData(mahasiswaList)
                            } else {
                                if (response.body() != null) {
                                    Log.e("", "onFailure: " + response.message())
                                }
                            }
                        } else {
                            loadingState.dismiss()
                            setData(null)
                        }
                    }

                    override fun onFailure(call: Call<MahasiswaResponse?>, t: Throwable) {
                        loadingState.dismiss()
                        Log.e("Retrofit Error", "onFailure: " + t.message)
                    }
                })
            }
        }
    }

    private fun setData(mahasiswaList: List<Mahasiswa>?) {
        if (mahasiswaList != null) {
            binding.tvNrp.text = mahasiswaList[0].nrp
            binding.tvNama.text = mahasiswaList[0].nama
            binding.tvEmail.text = mahasiswaList[0].email
            binding.tvJurusan.text = mahasiswaList[0].jurusan
        } else {
            binding.tvNrp.text = "Tidak ada mahasiswa dengan NRP ${binding.edtChckNrp.text}"
            binding.tvNama.text = ""
            binding.tvEmail.text = ""
            binding.tvJurusan.text = ""
        }
    }
}