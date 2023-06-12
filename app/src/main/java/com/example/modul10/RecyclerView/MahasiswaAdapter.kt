package com.example.modul10.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul10.Model.Mahasiswa
import com.example.modul10.databinding.ItemListBinding

class MahasiswaAdapter : RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>() {
    lateinit var onItemClick: ((Mahasiswa) -> Unit)
    lateinit var onDeleteClick: ((Mahasiswa) -> Unit)

    var mahasiswaList = arrayListOf<Mahasiswa>()

    fun setMahasiswa(mahasiswa: ArrayList<Mahasiswa>) {
        mahasiswaList.clear()
        mahasiswaList.addAll(mahasiswa)
        notifyDataSetChanged()
    }

    class ViewHolder (var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mahasiswaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mahasiswa = mahasiswaList[position]
        holder.binding.tvName.text = mahasiswa.nama
        holder.binding.tvEmail.text = mahasiswa.email
        holder.binding.tvNrp.text = mahasiswa.nrp
        holder.binding.tvJurusan.text = mahasiswa.jurusan

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mahasiswa)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick.invoke(mahasiswa)
        }
    }

}