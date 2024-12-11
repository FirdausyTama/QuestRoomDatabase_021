package com.example.praktikumpertemuan10.repository

import com.example.praktikumpertemuan10.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun  insertMhs(mahasiswa: Mahasiswa)
}