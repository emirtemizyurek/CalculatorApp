package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentNumber = ""
    private var operator = ""
    private var previousNumber = ""
    private var result = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
    }

    private fun setupListeners() {
        // Sayı butonları
        binding.apply {
            listOf(buttonZero, buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine).forEach { button ->
                button.setOnClickListener { appendNumber(button.text.toString()) }
            }

            // İşlem butonları
            buttonPlus.setOnClickListener { setOperator("+") }
            buttonSubs.setOnClickListener { setOperator("-") }
            buttonMultiply.setOnClickListener { setOperator("x") }
            buttonDivide.setOnClickListener { setOperator("÷") }

            // Diğer butonlar
            buttonEquals.setOnClickListener { calculateResult() }
            buttonClear.setOnClickListener { clear() }
            buttonComma.setOnClickListener { appendNumber(".") }
        }
    }

    private fun appendNumber(number: String) {
        currentNumber += number
        binding.textViewResult.text = currentNumber
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            operator = op
            previousNumber = currentNumber
            currentNumber = ""
        }
    }

    private fun calculateResult() {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
            result = when (operator) {
                "+" -> previousNumber.toDouble() + currentNumber.toDouble()
                "-" -> previousNumber.toDouble() - currentNumber.toDouble()
                "x" -> previousNumber.toDouble() * currentNumber.toDouble()
                "÷" -> previousNumber.toDouble() / currentNumber.toDouble()
                else -> 0.0
            }
            binding.textViewResult.text = result.toString()
            previousNumber = result.toString()
            currentNumber = ""
        }
    }

    private fun clear() {
        currentNumber = ""
        previousNumber = ""
        result = 0.0
        operator = ""
        binding.textViewResult.text = "0"
    }
}