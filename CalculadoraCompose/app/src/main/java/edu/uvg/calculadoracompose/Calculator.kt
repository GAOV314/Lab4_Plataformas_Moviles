/**
 * Universidad del Valle de Guatemala
 * Programacion de plataformas moviles
 * Gadiel Ocaña, 231270
 * 25/08/2024
 * Clase Calculadora, encargada de hacer las converisones entre tipos y evaluar la expresion
 */
package edu.uvg.calculadoracompose

import java.util.Stack
import kotlin.math.*
class Calculator {
    /**
     * @return el valor de procedencia
     * @param x caracter del operando a evaluar
     * Se encarga de evaluar los operandos y asignarle su valor de precedencia
     */
    fun priority(x: Char): Int {
        return when (x) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^', '√' -> 3
            else -> -1
        }
    }

    /**
     * @return cadena con expresion postfix
     * @param expression variable que guarda la expresion entrada en consola infix
     * La funcion convierte una expresion infix a postfix
     */
    fun infixToPostfix(expression: String): String {
        val stack = mutableListOf<Char>()
        val result = StringBuilder()
        var i = 0

        while (i < expression.length) {
            val char = expression[i]

            when {
                char.isDigit() -> {
                    // Leer números completos
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        result.append(expression[i])
                        i++
                    }
                    result.append(' ')  // Añadir un espacio para separar los números
                    continue
                }
                char == '(' -> stack.add(char)
                char == ')' -> {
                    while (stack.isNotEmpty() && stack.last() != '(') {
                        result.append(stack.removeAt(stack.size - 1)).append(' ')
                    }
                    stack.removeAt(stack.size - 1) // Remove '('
                }
                char in listOf('+', '-', '*', '/', '^', '√') -> {
                    while (stack.isNotEmpty() && priority(char) <= priority(stack.last())) {
                        result.append(stack.removeAt(stack.size - 1)).append(' ')
                    }
                    stack.add(char)
                }
            }
            i++
        }

        while (stack.isNotEmpty()) {
            result.append(stack.removeAt(stack.size - 1)).append(' ')
        }

        return result.toString().trim()
    }

    /**
     * @return retorna el valor de la expresion postfix en double
     * @param expression la variable guarda la expresion en postfix evaluada por la funcion infixToPostfix
     * La funcion se encarga de evaluar la expresion postfix y retornar un resultado numerico
     */
    fun evaluatePostfix(expression: String): Double {
        val stack = mutableListOf<Double>()
        val tokens = expression.split(" ")

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> stack.add(token.toDouble())
                token in listOf("+", "-", "*", "/", "^", "√") -> {
                    if (token == "√") {
                        val a = stack.removeAt(stack.size - 1)
                        stack.add(Math.sqrt(a))
                    } else {
                        val b = stack.removeAt(stack.size - 1)
                        val a = stack.removeAt(stack.size - 1)
                        val result = when (token) {
                            "+" -> a + b
                            "-" -> a - b
                            "*" -> a * b
                            "/" -> a / b
                            "^" -> Math.pow(a, b)
                            else -> 0.0
                        }
                        stack.add(result)
                    }
                }
            }
        }

        return stack.last()
    }


}