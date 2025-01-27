package cl.johnjaque.android.examen_programacion_ii.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
// Se define el interfaz como un DAO para poder interactuar con la base de datos.
@Dao
interface MedicionDao {
    // Se consulta a partir de todas las mediciones ordenadas por fecha descendente.
    @Query("SELECT * FROM Medicion ORDER BY fecha DESC")
    suspend fun obtenerTodos(): List<Medicion>
    // Se consulta una medición a partir de su ID.
    @Query("SELECT * FROM Medicion WHERE id = :id")
    suspend fun obtenerPorId(id: Long): Medicion
    // Se inserta una nueva medición en la base de datos.
    @Insert
    suspend fun insertar(medicion: Medicion)
    // Se modifica o actualiza una medición existente en la base de datos.
    @Update
    suspend fun modificar(medicion: Medicion)
    // Se elimina una medición de la base de datos.
    @Delete
    suspend fun eliminar(medicion: Medicion)
    // Se elimina todas las mediciones de la base de datos.
    @Query("DELETE FROM Medicion")
    suspend fun eliminarTodos()
}