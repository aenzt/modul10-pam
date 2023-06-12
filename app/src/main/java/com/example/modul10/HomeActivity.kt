package com.example.modul10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul10.API.APIConfig.apiService
import com.example.modul10.Helper.LoadingState
import com.example.modul10.Model.DeleteMahasiswaResponse
import com.example.modul10.Model.Mahasiswa
import com.example.modul10.Model.MahasiswaResponse
import com.example.modul10.RecyclerView.MahasiswaAdapter
import com.example.modul10.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    companion object {
        const val INTENT_ITEM = "intent_item"
    }

    private lateinit var binding : ActivityHomeBinding
    private lateinit var loadingState: LoadingState
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    private var listMahasiswa = ArrayList<Mahasiswa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        binding = ActivityHomeBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadingState = LoadingState(this@HomeActivity)

        getDataFromApi()
        setRecycle()

        mahasiswaAdapter.onItemClick = {mahasiswa: Mahasiswa ->
            startActivity(Intent(this@HomeActivity, DetailActivity::class.java).putExtra(INTENT_ITEM, mahasiswa))
        }

        mahasiswaAdapter.onDeleteClick = {mahasiswa: Mahasiswa ->
            deleteMahasiswa(mahasiswa)
        }

        binding.tvOption.setOnClickListener {
            startActivity(Intent(this@HomeActivity, AddMahasiswaActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getDataFromApi()
        mahasiswaAdapter.setMahasiswa(listMahasiswa)
    }

    private fun deleteMahasiswa(mahasiswa: Mahasiswa){
        loadingState.show()
        val client = apiService.deleteMahasiswa(mahasiswa.id)
        client.enqueue(object: Callback<DeleteMahasiswaResponse> {
            override fun onResponse(
                call: Call<DeleteMahasiswaResponse>,
                response: Response<DeleteMahasiswaResponse>
            ) {
                loadingState.dismiss()
                if(response.isSuccessful){
                    getDataFromApi()
                    mahasiswaAdapter.setMahasiswa(listMahasiswa)
                }
            }

            override fun onFailure(call: Call<DeleteMahasiswaResponse>, t: Throwable) {
                loadingState.dismiss()
            }

        })
    }

    private fun setRecycle(){
        mahasiswaAdapter = MahasiswaAdapter()
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mahasiswaAdapter
        }
        mahasiswaAdapter.setMahasiswa(listMahasiswa)
    }

    private fun getDataFromApi(){
        loadingState.show()
        val client = apiService.getAllMahasiswa()
        client.enqueue(object : Callback<MahasiswaResponse> {
            override fun onResponse(
                call: Call<MahasiswaResponse>,
                response: Response<MahasiswaResponse>
            ) {
                loadingState.dismiss()
                if(response.isSuccessful){
                    if (response.body() != null){
                        listMahasiswa = response.body()!!.data as ArrayList<Mahasiswa>
                        listMahasiswa.reverse()
                        mahasiswaAdapter.setMahasiswa(listMahasiswa)
                    }
                }
            }
            override fun onFailure(call: Call<MahasiswaResponse>, t: Throwable) {
                loadingState.dismiss()
                Log.e("Retrofit Error", "onFailure: " + t.message)
            }
        })
    }
}