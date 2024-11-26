package com.example.calculator.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.Black_Gray
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.Gray
import com.example.calculator.ui.theme.Orange
import com.example.calculator.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    label: String = "",
    isOperation: Boolean = false,
    isSpecial: Boolean = false,
    onButtonPress: (String) -> Unit
) {
    val backgroundColor = when {
        isOperation -> Orange
        isSpecial -> Black_Gray
        else -> Gray
    }

    Box(
        modifier = modifier
            .padding(10.dp)
            .aspectRatio(1f)
            .background(backgroundColor, shape = CircleShape)
            .clickable { onButtonPress(label) }
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = label,
                tint = White,
                modifier = Modifier.fillMaxSize()

            )
        } else {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = White

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalcButtonPreview() {
    CalculatorTheme {
        CalcButton (
            label = "AC"
        ){

        }
    }
}