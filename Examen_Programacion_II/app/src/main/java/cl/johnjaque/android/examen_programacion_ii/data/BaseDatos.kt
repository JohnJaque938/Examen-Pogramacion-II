package cl.johnjaque.android.examen_programacion_ii.data
// Importaciones relacionadas con la creación de la base de datos y el manejo de Room.
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Medicion::class], version = 1) // Establece la base de datos de Room
@TypeConverters(LocalDateConverter::class)
abstract class BaseDatos : RoomDatabase() {
    abstract fun medicionDao(): MedicionDao
}