package com.example.calculator.ui

class CalculatorBrain {
    enum class Operation (val op: String) {
        SUM("+"),
        SUB("-"),
        MULT("*"),
        DIV("/"),
        SQRT("√"),
        SIGNAL("±"),
        PERCENT("%"),
        RAND("");

        companion object {
            fun getOp(value: String): Operation {
                return entries.find { it.op == value } ?: RAND
            }

            var operation : Operation? = null
            var operand : Double? = 0.0

        fun doOperation(value: Double): Double {
            return when(operation) {
                Operation.SUM -> value + operand
                Operation.SUB -> value - operand
                Operation.MULT -> value * operand
                Operation.DIV -> value / operand
                Operation.SQRT -> Math.sqrt(value)
                Operation.SIGNAL -> value * -1
                Operation.PERCENT -> value / 100
                Operation.RAND -> Math.random()
                else -> value

            }


        }
    }

    }
}