package com.example.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val buttonList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "AC", "0", ".", "=",
)

@Composable
fun Calculator(modifier: Modifier = Modifier, calculatorViewModel: CalculatorViewModel) {
    val expressionText = calculatorViewModel.equationText.observeAsState()
    val result = calculatorViewModel.result.observeAsState()
    Box(
        modifier = Modifier.padding(top = 80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = expressionText.value?:"",
                style = TextStyle(
                    fontSize = 30.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = result.value?:"",
                style = TextStyle(
                    fontSize = 60.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(15.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                items(buttonList) {
                    CalculatorButton(btn = it, onClick = {
                        calculatorViewModel.onButtonClicked(it)
                    })
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(btn: String, onClick:()->Unit) {
    Box(Modifier.padding(8.dp)) {
        ElevatedButton(
            onClick = onClick,
            modifier = Modifier
                .size(80.dp)
                .padding(2.dp),
            shape = CircleShape,
            colors = ButtonColors(
                containerColor = getColor(btn),
                contentColor = Color.White,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            )
        ) {

            Text(
                text = btn,
                fontSize = 22.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getColor(btn: String): Color {
    if (btn == "AC" || btn == "C") {
        return Color(0xFFF44336)
    }
    if (btn == "(" || btn == ")") {
        return Color.Gray
    }
    return if (btn == "/" || btn == "*" || btn == "-" || btn == "+") {
        Color(0xFFFF9800)
    } else {
        Color(0xFF00C8C9)
    }
}