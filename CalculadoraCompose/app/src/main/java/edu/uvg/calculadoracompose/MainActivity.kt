/**
 * Universidad del Valle de Guatemala
 * Programacion de plataformas moviles
 * Gadiel Ocaña, 231270
 * 25/08/2024
 * Clase main activity encargada del UI de la calculadora
 */
package edu.uvg.calculadoracompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Clase principal de la actividad que extiende ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Define el contenido de la UI con la función composable CalculatorApp
            CalculatorApp()
        }
    }
}

// Función composable que define la interfaz de la calculadora
@Composable
fun CalculatorApp() {
    // Crea una instancia de la clase Calculator para usar la lógica de cálculo
    val calculator = Calculator()
    // Variables de estado para la expresión matemática y el resultado
    var expression by remember { mutableStateOf("0") } // Inicializa la expresión con "0"
    var result by remember { mutableStateOf("") } // Inicializa el resultado vacío

    // Estructura de diseño en columna para organizar los componentes verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible
            .padding(16.dp), // Añade un margen alrededor del contenido
        verticalArrangement = Arrangement.SpaceBetween, // Distribuye el espacio entre los elementos
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente los elementos
    ) {
        // Muestra la expresión ingresada en un texto grande
        Text(
            text = expression, // Texto que muestra la expresión actual
            fontSize = 32.sp, // Tamaño de fuente del texto
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho disponible
                .padding(16.dp) // Añade un margen al texto
        )

        // Muestra el resultado de la expresión
        Text(
            text = result, // Texto que muestra el resultado
            fontSize = 24.sp, // Tamaño de fuente del resultado
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho disponible
                .padding(16.dp), // Añade un margen al resultado
            color = MaterialTheme.colorScheme.primary // Color del texto basado en el tema actual
        )

        // Crea una columna adicional para los botones de la calculadora
        Column(
            modifier = Modifier.fillMaxWidth() // La columna ocupa todo el ancho disponible
        ) {
            // Define los botones en una lista de listas para organizar en filas
            val buttons = listOf(
                listOf("7", "8", "9", "/"), // Primera fila de botones
                listOf("4", "5", "6", "*"), // Segunda fila de botones
                listOf("1", "2", "3", "-"), // Tercera fila de botones
                listOf(".", "0", "=", "+"), // Cuarta fila de botones
                listOf("(", ")", "^", "√"), // Quinta fila de botones
                listOf("CLEAR", "DEL") // Sexta fila con opciones de borrar
            )

            // Recorre cada fila de botones
            buttons.forEach { row ->
                // Crea una fila para los botones actuales
                Row(
                    modifier = Modifier.fillMaxWidth(), // La fila ocupa todo el ancho disponible
                    horizontalArrangement = Arrangement.SpaceEvenly // Espacia uniformemente los botones
                ) {
                    // Recorre cada botón en la fila
                    row.forEach { label ->
                        // Define el botón
                        Button(
                            onClick = {
                                // Maneja los clics en los botones
                                when (label) {
                                    "=" -> { // Si el botón es "=" calcula el resultado
                                        try {
                                            val postfix = calculator.infixToPostfix(expression) // Convierte a postfix
                                            result = calculator.evaluatePostfix(postfix).toString() // Evalúa y asigna el resultado
                                        } catch (e: Exception) {
                                            result = "Error" // Muestra "Error" si hay un problema
                                        }
                                    }
                                    "CLEAR" -> { // Si el botón es "CLEAR", resetea la expresión y el resultado
                                        expression = "0"
                                        result = ""
                                    }
                                    "DEL" -> { // Si el botón es "DEL", elimina el último carácter
                                        if (expression.isNotEmpty()) {
                                            expression = expression.dropLast(1) // Elimina el último carácter
                                            if (expression.isEmpty()) expression = "0" // Si la expresión queda vacía, muestra "0"
                                        }
                                    }
                                    else -> { // Para otros botones, añade el texto a la expresión
                                        if (expression == "0") {
                                            expression = label // Reemplaza "0" por el nuevo valor
                                        } else {
                                            expression += label // Añade el nuevo valor a la expresión
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f) // Ocupa el mismo espacio que otros botones en la fila
                                .padding(4.dp) // Añade un pequeño margen alrededor del botón
                        ) {
                            Text(text = label, fontSize = 24.sp) // Muestra el texto del botón
                        }
                    }
                }
            }
        }
    }
}

// Función de previsualización para ver cómo se verá la calculadora en Android Studio
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorApp() // Llama a la función composable principal
}