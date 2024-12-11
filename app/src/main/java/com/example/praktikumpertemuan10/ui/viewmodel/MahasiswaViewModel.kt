package com.example.praktikumpertemuan10.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumpertemuan10.data.entity.Mahasiswa
import com.example.praktikumpertemuan10.repository.LocalRepositoryMhs
import com.example.praktikumpertemuan10.repository.RepositoryMhs
import kotlinx.coroutines.launch

//data class variabel yang mentimpan
// data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

// menyimpan input form ke dalam entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null
    ){
    fun isValid(): Boolean {
        return nim == null
                && nama == null
                && jenisKelamin == null
                && alamat == null
                && kelas == null
                && angkatan == null
    }
}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMassage: String? = null,
)

class MahasiswaViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel(){

    var uiState by mutableStateOf(MhsUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    // Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return  errorState.isValid()
    }

    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackBarMassage = "Data berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(), //reset input form
                        isEntryValid = FormErrorState() //reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMassage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMassage = "Input tidak valid. Periksa kembali data anda."
            )
        }
    }

    //reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMassage = null)
    }
}

