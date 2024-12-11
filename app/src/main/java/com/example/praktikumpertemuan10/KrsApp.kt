package com.example.praktikumpertemuan10

import android.app.Application
import com.example.praktikumpertemuan10.dependenciesinjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp // fungsinya untuk menyimpan instance
    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // membuat instance containerapp
        //instance adalahh object yang divuat dari class
    }
}