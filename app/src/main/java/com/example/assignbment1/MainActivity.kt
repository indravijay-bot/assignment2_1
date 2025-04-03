package com.example.assignbment1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spinnerFrom: Spinner = findViewById(R.id.spinner)
        val spinnerTo: Spinner = findViewById(R.id.spinner2)
        val editTextValue: EditText = findViewById(R.id.textInputEditText)
        val buttonConvert: Button = findViewById(R.id.button)
        val textViewResult: TextView = findViewById(R.id.textView2)

        val unitOptions = arrayOf("Inch", "Yard", "Foot", "Mile", "Ounce", "Pound", "Ton","Fahrenheit", "Celsius",  "Kelvin")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Handle button click for conversion
        buttonConvert.setOnClickListener {
            val inputText = editTextValue.text.toString()

            // Checking if input is empty
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a value to convert", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert input to Double format
            val inputValue = inputText.toDoubleOrNull()
            if (inputValue == null) {
                Toast.makeText(this, "Invalid input! Please enter a numeric value.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromUnit = spinnerFrom.selectedItem.toString()
            val toUnit = spinnerTo.selectedItem.toString()

            // Checking if source and dest units are the same units
            if (fromUnit == toUnit) {
                Toast.makeText(this, "Please select different units for conversion", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Perform conversions
            val result = convertUnits(fromUnit, toUnit, inputValue)

            // Display the answer
            if (result != null) {
                textViewResult.text = "Converted Value is: $result"
            } else {
                Toast.makeText(this, "Invalid conversion. Please check unit selection.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun convertUnits(fromUnit: String, toUnit: String, value: Double): Double? {
        return when {
            // Length conversion logic
            fromUnit == "Inch" && toUnit == "Foot" -> value / 12
            fromUnit == "Inch" && toUnit == "Yard" -> value / 36
            fromUnit == "Foot" && toUnit == "Yard" -> value / 3
            fromUnit == "Foot" && toUnit == "Mile" -> value / 5280
            fromUnit == "Yard" && toUnit == "Inch" -> value * 36
            fromUnit == "Yard" && toUnit == "Foot" -> value * 3
            fromUnit == "Inch" && toUnit == "Mile" -> value / 63360
            fromUnit == "Foot" && toUnit == "Inch" -> value * 12
            fromUnit == "Mile" && toUnit == "Inch" -> value * 63360
            fromUnit == "Mile" && toUnit == "Foot" -> value * 5280
            fromUnit == "Mile" && toUnit == "Yard" -> value * 1760
            fromUnit == "Yard" && toUnit == "Mile" -> value / 1760


            // Weight conversion logic
            fromUnit == "Pound" && toUnit == "Ounce" -> value * 16
            fromUnit == "Pound" && toUnit == "Ton" -> value / 2000
            fromUnit == "Ounce" && toUnit == "Ton" -> value / 32000
            fromUnit == "Ton" && toUnit == "Pound" -> value * 2000
            fromUnit == "Ton" && toUnit == "Ounce" -> value * 32000
            fromUnit == "Ounce" && toUnit == "Pound" -> value / 16


            // Temperature conversion logic
            fromUnit == "Celsius" && toUnit == "Fahrenheit" -> (value * 1.8) + 32
            fromUnit == "Kelvin" && toUnit == "Celsius" -> value - 273.15
            fromUnit == "Fahrenheit" && toUnit == "Kelvin" -> (value - 32) / 1.8 + 273.15
            fromUnit == "Kelvin" && toUnit == "Fahrenheit" -> (value - 273.15) * 1.8 + 32
            fromUnit == "Fahrenheit" && toUnit == "Celsius" -> (value - 32) / 1.8
            fromUnit == "Celsius" && toUnit == "Kelvin" -> value + 273.15


            else -> null
        }
    }
}