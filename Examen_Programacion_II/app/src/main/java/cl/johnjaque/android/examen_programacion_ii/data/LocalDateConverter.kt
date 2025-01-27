package cl.johnjaque.android.examen_programacion_ii.data

import androidx.room.TypeConverter // Se importa la anotación para convertir tipos en Room.
import java.time.LocalDate // Se importa la clase LocalDate para manejar fechas.

class LocalDateConverter {
    // Se Indica que este metodo se convierte de Long a LocalDate.
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }
    // Indica que este metodo se convierte de LocalDate a Long.
    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}