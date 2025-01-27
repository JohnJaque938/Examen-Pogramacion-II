package cl.johnjaque.android.examen_programacion_ii

import android.app.Application
import androidx.room.Room
import cl.johnjaque.android.examen_programacion_ii.data.BaseDatos
// Clase que extiende de Application para la configuración global de la app.
class Aplicacion : Application() {
    // Crea la base de datos usando Room y obtiene el DAO de la base de datos.
    val db by lazy { Room.databaseBuilder(this, BaseDatos::class.java, "mediciones.db").build() }
    val medicionDao by lazy { db.medicionDao() }
}