package cl.johnjaque.android.examen_programacion_ii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import java.text.DecimalFormat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.johnjaque.android.examen_programacion_ii.data.Medicion
import cl.johnjaque.android.examen_programacion_ii.ui.ListaMedicionesViewModel
import java.time.LocalDate
// MainActivity que inicia la UI de la aplicación.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMedicionesUI() // Carga la UI de la aplicación
        }
    }
}
// Función Composable que organiza la UI principal con navegación.
@Composable
fun AppMedicionesUI(
    navController: NavHostController = rememberNavController(),
    vmListaMediciones: ListaMedicionesViewModel = viewModel(factory = ListaMedicionesViewModel.Factory)
) {
    LaunchedEffect(Unit) { vmListaMediciones.obtenerMediciones() }
    // Se configura las pantallas (vistas) y la navegación entre ellas.
    NavHost(navController, startDestination = "inicio") {
        composable("inicio") {
            PantallaListaMediciones(
                mediciones = vmListaMediciones.mediciones,
                onAdd = { navController.navigate("form") },
                onDeleteAll = { vmListaMediciones.eliminarTodos() }
            )
        }
        composable("form") {
            PantallaFormMedicion(
                onMedicionGuardada = { navController.popBackStack() },
                vmListaMediciones = vmListaMediciones
            )
        }
    }
}
// Composable que muestra la lista de mediciones y un botón para agregar una nueva.
@Composable
fun PantallaListaMediciones(
    mediciones: List<Medicion>,
    onAdd: () -> Unit,
    onDeleteAll: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Medición")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(mediciones) { medicion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icono según el tipo de medición seleccionada
                        Icon(
                            imageVector = when (medicion.tipo) {
                                "Agua" -> Icons.Filled.WaterDrop // Icono para el Agua
                                "Luz" -> Icons.Filled.Lightbulb // Icono para la Luz
                                "Gas" -> Icons.Filled.LocalFireDepartment // Icono para el Gas
                                else -> Icons.Filled.Warning // Otro ícono por defecto
                            },
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = medicion.tipo,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            // Muestra el valor de la medición formateado con un separador de miles
                            val decimalFormat = DecimalFormat("#,###")
                            Text(
                                text = decimalFormat.format(medicion.valor),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        // Muestra la fecha de la medición.
                        Text(
                            text = medicion.fecha.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            // Botón para eliminar todas las mediciones al final de la lista.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter // Centrado al fondo
            ) {
                Button(
                    onClick = onDeleteAll,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(0.7f) // Ajusta el tamaño al 70% del su ancho
                ) {
                    Text("Eliminar Mediciones")
                }
            }
        }
    }
}
// Composable que muestra el formulario para agregar o registrar una nueva medición.
@Composable
fun PantallaFormMedicion(
    vmListaMediciones: ListaMedicionesViewModel,
    onMedicionGuardada: () -> Unit
) {
    var tipo by remember { mutableStateOf("Agua") }
    var valor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    val tipos = listOf("Agua", "Luz", "Gas")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Se centra el formulario
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Se centra los elementos dentro del formulario
            modifier = Modifier.padding(16.dp)
        ) {
            // Titulo del formulario
            Text(
                text = "Registro Medidor",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Campo de texto que permite ingresar el valor del medidor.
            TextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Medidor") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Campo de texto que permite ingresar la fecha de la medición.
            TextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // RadioButtons que permite seleccionar el tipo de medidor (agua, luz y gas).
            Text("Medidor de:")
            tipos.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    RadioButton(selected = tipo == it, onClick = { tipo = it })
                    Text(it, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para agregar o registrar la medición.
            Button(
                onClick = {
                    val nuevaMedicion = Medicion(
                        tipo = tipo,
                        valor = valor.toDouble(),
                        fecha = LocalDate.parse(fecha)
                    )
                    vmListaMediciones.insertarMedicion(nuevaMedicion)
                    onMedicionGuardada()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Medición")
            }
        }
    }
}