package com.example.calculator

sealed class CalculatorOperation(val symbol: String) {
    object Add: CalculatorOperation(symbol = "+")
    object Substract: CalculatorOperation(symbol = "-")
    object Multiply: CalculatorOperation(symbol = "×")
    object Divide: CalculatorOperation(symbol = "÷")

    object Power : CalculatorOperation("^")
    object Sqrt : CalculatorOperation("√")
    object Factorial : CalculatorOperation("!")
    object Reciprocal : CalculatorOperation("1/x")

    object Sin : CalculatorOperation("sin")
    object Cos : CalculatorOperation("cos")
    object Tan : CalculatorOperation("tan")
    object Asin : CalculatorOperation("sin⁻¹")
    object Acos : CalculatorOperation("cos⁻¹")
    object Atan : CalculatorOperation("tan⁻¹")

    object Log : CalculatorOperation("log")
    object Ln : CalculatorOperation("ln")



}