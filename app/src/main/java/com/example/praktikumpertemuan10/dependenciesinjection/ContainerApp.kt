package com.example.praktikumpertemuan10.dependenciesinjection

import android.content.Context
import com.example.praktikumpertemuan10.data.database.KrsDatabase
import com.example.praktikumpertemuan10.repository.LocalRepositoryMhs
import com.example.praktikumpertemuan10.repository.RepositoryMhs

interface InterFaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterFaceContainerApp{
    override val repositoryMhs: RepositoryMhs by lazy{
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}