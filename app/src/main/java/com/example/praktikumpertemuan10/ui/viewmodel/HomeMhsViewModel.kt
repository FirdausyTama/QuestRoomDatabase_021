package com.example.praktikumpertemuan10.ui.viewmodel

import com.example.praktikumpertemuan10.data.entity.Mahasiswa

class HomeMhsViewModel(
    private
) {

}

data class HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)