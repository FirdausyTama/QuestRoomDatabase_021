package com.example.praktikumpertemuan10.ui.view.mahasiswa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikumpertemuan10.ui.costumwidget.TopAppBar
import com.example.praktikumpertemuan10.ui.viewmodel.PenyediaViewModel
import com.example.praktikumpertemuan10.ui.viewmodel.UpdateMhsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMhsViewModel = viewModel(factory = PenyediaViewModel.Fatory)//inisialisasi viewmodel
){
    val uiState = viewModel.updateUIState //ambil ui state dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() }//snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackBarMessage

    LaunchedEffect(uiState.snackBarMassage) {
        println("LaunchedEffect triggered")
        uiState.snackBarMassage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }


    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}, // tempatkan snackbar di scafold
        topBar = {
            TopAppBar(
                judul = "Edit Mahasiswa",
                showBackButton = true,
                onBack = onBack,
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){

            //Isi Body
            InsertBodymhs(
                uiState = uiState,
                onValueChange = {updatedEvent ->
                    viewModel.updateState(updatedEvent) //Update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateField()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() //Navigasi di main thread
                            }
                        }
                    }
                }
            )
        }
    }
}