package com.example.praktikumpertemuan10.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.praktikumpertemuan10.data.entity.Mahasiswa

@Dao
interface MahasiswaDao {
    @Insert
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
}