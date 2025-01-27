package cl.johnjaque.android.examen_programacion_ii.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
// Se establece o define la entidad de Room con el nombre Medicion.
@Entity
// Clase de datos para representar una medición.
data class Medicion(
    @PrimaryKey(autoGenerate = true) var id: Long? = null, // Clave primaria, que se genera automáticamente.
    var tipo: String, // El tipo de medición (agua, luz y gas).
    var valor: Double, // El valor de la medición.
    var fecha: LocalDate // La fecha de la medición
)