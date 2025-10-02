package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.MediumGray
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                val viewModel: CalculatorViewModel by viewModels()
                val state = viewModel.state
                val buttonSpacing = 8.dp

                // state untuk toggle scientific/basic
                var showScientific by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MediumGray)
                        .padding(16.dp)
                ) {
                    if (showScientific) {
                        ScientificCalculator(
                            state = state,
                            onAction = viewModel::onAction,
                            buttonSpacing = buttonSpacing,
                            modifier = Modifier
                                .fillMaxSize(),
                            showScientific = showScientific,
                            onToggleScientific = { showScientific = !showScientific }
                        )
                    } else {
                        CalculatorWork(
                            state = state,
                            onAction = viewModel::onAction,
                            buttonSpacing = buttonSpacing,
                            modifier = Modifier
                                .fillMaxSize(),
                            showScientific = showScientific,
                            onToggleScientific = { showScientific = !showScientific }
                        )
                    }
                }
            }
        }
    }
}
