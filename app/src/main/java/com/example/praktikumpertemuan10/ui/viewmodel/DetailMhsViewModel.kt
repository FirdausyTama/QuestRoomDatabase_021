package com.example.praktikumpertemuan10.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumpertemuan10.data.entity.Mahasiswa
import com.example.praktikumpertemuan10.repository.RepositoryMhs
import com.example.praktikumpertemuan10.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,

    ) : ViewModel(){
        private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

}

data class DetailUiState(
    val detailUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""

){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MahasiswaEvent()
    val isUiEventNotEmpety: Boolean
        get() = detailUiEvent != MahasiswaEvent()
}

/*
* Data class untuk menampung data yang akan di tampilkan di ui
 */

//memindahkan data dari entity ke ui
fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent {
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}