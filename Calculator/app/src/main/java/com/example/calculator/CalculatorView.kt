package com.example.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.components.CalcButton
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.Pink40

@Composable
fun CalculatorView(modifier: Modifier = Modifier){

    val icon = painterResource(id = R.drawable.calculator)

    var display by remember {mutableStateOf("0")}
    var operator by remember { mutableDoubleStateOf(0.0) }
    var operation by remember {mutableStateOf("")}

    var userIsInTheMiddleOfTyping by remember { mutableStateOf(false) }

    fun getDisplay() : Double {
        return display.toDouble()
    }

    fun setDisplay(value: Double) {
        if ( value % 1 == 0.0) {
            display = value.toInt().toString()
        } else {
            display = value.toString()
        }
    }

    val onOpPress : (String) -> Unit = { op ->
        userIsInTheMiddleOfTyping = false
        if (operation.isNotEmpty()) {
            val result = when (operation) {
                "+" -> operator + getDisplay()
                "-" -> operator - getDisplay()
                "*" -> operator * getDisplay()
                "/" -> operator / getDisplay()
                else -> {
                    operation = ""
                    getDisplay()
                }
            }
            setDisplay(result)
        }

        operator = display.toDouble()
        operation = op

    }


        val onNumPress : (String) -> Unit = { num ->
        if(userIsInTheMiddleOfTyping) {
            if (display == "0") {
                if (num == ".") {
                    display = "0."
                } else {
                    display = num
                }
            } else {
                if (num == ".") {
                    if (!display.contains(".")) {
                        display += num
                    }
                } else {
                    display += num
                }
            }
        }else{
            display = num
        }

        userIsInTheMiddleOfTyping = true
    }



Column (modifier = modifier) {
    Text(text = display,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.End)

    Row{
        CalcButton(modifier= Modifier.weight(1f),
            label = "AC",
            isSpecial = true,
            onButtonPress = {display = ""})
        CalcButton(modifier= Modifier.weight(1f),
            label = "⁺⁄₋",
            isSpecial = true,
            onButtonPress = {display = ""})
        CalcButton(modifier= Modifier.weight(1f),
            label = "%",
            isSpecial = true,
            onButtonPress = {display = "%"})
        CalcButton(modifier= Modifier.weight(1f),
        label = "÷",
        onButtonPress = onOpPress,
            isOperation=true)
        }
    Row{
        CalcButton(modifier= Modifier.weight(1f),
            label = "7",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "8",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "9",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "x",
            onButtonPress = onOpPress,
            isOperation=true)
    }
    Row{
        CalcButton(modifier= Modifier.weight(1f),
            label = "4",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "5",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "6",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "-",
            onButtonPress = onOpPress,
            isOperation=true)
    }
    Row{
        CalcButton(modifier= Modifier.weight(1f),
            label = "1",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "2",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "3",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "+",
            onButtonPress = onOpPress,
            isOperation=true)
    }
    Row{
        CalcButton(modifier= Modifier.weight(1f),
            icon = icon,
            label = "Calc",
            onButtonPress = {display=""})
        CalcButton(modifier= Modifier.weight(1f),
            label = "0",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = ".",
            onButtonPress = onNumPress)
        CalcButton(modifier= Modifier.weight(1f),
            label = "=",
            onButtonPress = onOpPress,
            isOperation=true)
    }
    }

}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorView()
    }
}