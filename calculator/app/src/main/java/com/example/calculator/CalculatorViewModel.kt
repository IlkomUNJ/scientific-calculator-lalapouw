package com.example.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.*

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction){
        when(action){
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
        }
    }

    private fun performDeletion(){
        when {
            state.number2.isNotBlank() -> {
                state = state.copy(number2 = state.number2.dropLast(1))
            }
            state.operation != null -> {
                state = state.copy(operation = null)
            }
            state.number1.isNotBlank() -> {
                state = state.copy(number1 = state.number1.dropLast(1))
            }
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        if (number1 != null && number2 != null && state.operation != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Substract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> if (number2 != 0.0) number1 / number2 else Double.NaN
                is CalculatorOperation.Power -> number1.pow(number2)
                else -> return
            }
            state = state.copy(
                number1 = formatResult(result),
                number2 = "",
                operation = null
            )
            return
        }

        if (number1 != null && state.operation != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Sqrt -> if (number1 >= 0) sqrt(number1) else Double.NaN
                is CalculatorOperation.Reciprocal -> if (number1 != 0.0) 1 / number1 else Double.NaN
                is CalculatorOperation.Factorial -> factorial(number1.toInt())

                is CalculatorOperation.Sin -> sin(Math.toRadians(number1))
                is CalculatorOperation.Cos -> cos(Math.toRadians(number1))
                is CalculatorOperation.Tan -> tan(Math.toRadians(number1))

                is CalculatorOperation.Asin -> if (number1 in -1.0..1.0) Math.toDegrees(asin(number1)) else Double.NaN
                is CalculatorOperation.Acos -> if (number1 in -1.0..1.0) Math.toDegrees(acos(number1)) else Double.NaN
                is CalculatorOperation.Atan -> Math.toDegrees(atan(number1))

                is CalculatorOperation.Log -> if (number1 > 0) log10(number1) else Double.NaN
                is CalculatorOperation.Ln -> if (number1 > 0) ln(number1) else Double.NaN

                else -> return
            }
            state = state.copy(
                number1 = formatResult(result),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperation){
        if (state.number1.isBlank()) {
            // belum ada angka, simpan operasi saja
            state = state.copy(operation = operation)
        } else {
            // kalau sudah ada angka, langsung simpan operasi
            state = state.copy(operation = operation)
        }
    }

    private fun factorial(n: Int): Double {
        if (n < 0) return Double.NaN
        var result = 1.0
        for (i in 1..n) {
            result *= i
        }
        return result
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "Error"
        return value.toString().take(15).removeSuffix(".0")
    }

    private fun enterDecimal(){
        if(state.operation == null && !state.number1.contains(".")
            && state.number1.isNotBlank()
        ) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if(!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(number2 = state.number2 + ".")
            return
        }
    }

    private fun enterNumber(number: Int){
        val op = state.operation

        if (op == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) return
            state = state.copy(number1 = state.number1 + number)
            return
        }

        if (op is CalculatorOperation.Sin ||
            op is CalculatorOperation.Cos ||
            op is CalculatorOperation.Tan ||
            op is CalculatorOperation.Log ||
            op is CalculatorOperation.Ln ||
            op is CalculatorOperation.Sqrt ||
            op is CalculatorOperation.Reciprocal ||
            op is CalculatorOperation.Factorial ||
            op is CalculatorOperation.Asin ||
            op is CalculatorOperation.Acos ||
            op is CalculatorOperation.Atan
        ) {
            if (state.number1.length >= MAX_NUM_LENGTH) return
            state = state.copy(number1 = state.number1 + number)
            return
        }

        if (state.number2.length >= MAX_NUM_LENGTH) return
        state = state.copy(number2 = state.number2 + number)
    }



    companion object {
        private const val MAX_NUM_LENGTH = 12
    }
}
