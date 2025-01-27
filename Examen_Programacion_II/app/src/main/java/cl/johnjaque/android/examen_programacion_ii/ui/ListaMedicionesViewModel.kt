package cl.johnjaque.android.examen_programacion_ii.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.johnjaque.android.examen_programacion_ii.Aplicacion
import cl.johnjaque.android.examen_programacion_ii.data.Medicion
import cl.johnjaque.android.examen_programacion_ii.data.MedicionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
// ViewModel que controla la lógica de la lista de mediciones.
class ListaMedicionesViewModel(private val medicionDao: MedicionDao) : ViewModel() {
    // Estado mutable para guardar la lista de mediciones.
    var mediciones by mutableStateOf(listOf<Medicion>())
    // Función para insertar una nueva medición.
    fun insertarMedicion(medicion: Medicion) {
        viewModelScope.launch(Dispatchers.IO) {
            medicionDao.insertar(medicion)
            obtenerMediciones()
        }
    }
    // Función para obtener todas las mediciones almacenadas.
    fun obtenerMediciones(): List<Medicion> {
        viewModelScope.launch(Dispatchers.IO) {
            mediciones = medicionDao.obtenerTodos()
        }
        return mediciones
    }
    // Función para eliminar todas las mediciones almacenadas.
    fun eliminarTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            medicionDao.eliminarTodos()
            obtenerMediciones() // Actualizamos la lista de mediciones
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                ListaMedicionesViewModel(aplicacion.medicionDao)
            }
        }
    }
}