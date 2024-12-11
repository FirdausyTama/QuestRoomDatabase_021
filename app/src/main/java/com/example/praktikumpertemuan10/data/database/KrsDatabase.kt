package com.example.praktikumpertemuan10.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.praktikumpertemuan10.data.dao.MahasiswaDao
import com.example.praktikumpertemuan10.data.entity.Mahasiswa

//mendefinisikan database dengan tabel mahasiswa
@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase(){

    //Mendefinisikan fungsi untuk mengakses data mahasiswa
    abstract fun mahasiswaDao(): MahasiswaDao

    companion object{
        @Volatile // memastikan bahwa nilai nvariabel instance selalu sama di semua
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java, //Class Database
                    "KrsDatabase" //nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}