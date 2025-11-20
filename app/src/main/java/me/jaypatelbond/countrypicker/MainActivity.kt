package me.jaypatelbond.countrypicker

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import me.jaypatelbond.countrypicker.model.Country

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DemoScreen()
            }
        }
    }

    @Composable
    fun DemoScreen() {
        var showComposePicker by remember { mutableStateOf(false) }
        var selectedCountryCompose by remember { mutableStateOf<Country?>(null) }
        var selectedCountryFragment by remember { mutableStateOf<Country?>(null) }

        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Text(
                    text = "Country Picker Demo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Try both implementations below",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Compose Implementation Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎨 Compose Implementation",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "For modern Compose apps",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { showComposePicker = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Open Compose Picker")
                        }

                        selectedCountryCompose?.let { country ->
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Selected: ${country.flag} ${country.name}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = country.dialCode,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Fragment Implementation Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📱 Fragment Implementation",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "For legacy View-based apps",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = { showFragmentPicker(selectedCountryFragment) { selectedCountryFragment = it } },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Open Fragment Picker")
                        }

                        selectedCountryFragment?.let { country ->
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Selected: ${country.flag} ${country.name}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = country.dialCode,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigate to Fragment Demo Button
                Button(
                    onClick = {
                        startActivity(Intent(this@MainActivity, FragmentDemoActivity::class.java))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Show Fragment Demo (XML)")
                }
            }
        }

        // Compose Picker
        if (showComposePicker) {
            CountryPickerBottomSheet(
                onDismiss = { showComposePicker = false },
                onCountrySelected = { country ->
                    selectedCountryCompose = country
                    showComposePicker = false
                },
                preSelectedCountryCode = selectedCountryCompose?.code,
                style = CountryPickerStyle(
                    titleText = "Choose Your Country",
                    titleFontSize = 24.sp,
                    titleColor = Color(0xFF6200EE),
                    searchHintText = "Type to search...",
                    countryNameFontSize = 18.sp,
                    dialCodeFontSize = 16.sp,
                    flagFontSize = 28.sp,
                    selectedCountryColor = Color(0xFF6200EE),
                    enableItemAnimation = true,
                    enableSearchAnimation = true,
                    animationDurationMillis = 400
                )
            )
        }
    }

    /**
     * Shows the Fragment-based country picker.
     * This demonstrates how to use CountryPickerBottomSheetFragment in a traditional Activity.
     */
    private fun showFragmentPicker(currentSelection: Country?, onSelected: (Country) -> Unit) {
        val picker = CountryPickerBottomSheetFragment().apply {
            // Set custom country list (optional)
            // customCountryList = listOf(...)

            // Set pre-selected country (optional)
            preSelectedCountryCode = currentSelection?.code

            // Set selection callback
            onCountrySelectedListener = { country ->
                onSelected(country)
            }
        }

        // Show the fragment
        picker.show(supportFragmentManager, "CountryPickerFragment")
    }
}
