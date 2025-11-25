# 🌍 Country Picker Library for Android

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.5.0-blue.svg)](https://developer.android.com/jetpack/compose)

A beautiful, fully customizable country picker component for Android with Jetpack Compose support. Features real-time search, smooth animations, and extensive styling options.

## ✨ Features

- 🎨 **Fully Customizable** - Control every aspect of the UI (colors, fonts, sizes, spacing)
- 🔍 **Smart Search** - Real-time filtering by country name, code, or dial code
- ✨ **Smooth Animations** - Beautiful fade-in, slide, and scale animations
- 🌍 **All Countries** - Built-in support for 50+ countries with flag emojis
- 📱 **Material Design 3** - Modern Material Design 3 theming
- 🎯 **Pre-selection** - Highlight a pre-selected country
- 🔧 **Flexible** - Use custom country lists or the built-in comprehensive list
- 🎭 **Two Implementations** - Pure Compose or Fragment-based for legacy apps
- ⚡ **Lightweight** - Minimal dependencies, small footprint
- 🌐 **Localization Ready** - Easy to customize for different languages

## 📦 Installation

### Gradle (Kotlin DSL)

Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Jaypatelbond:CountryPicker:v1.0.0")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'com.github.Jaypatelbond:CountryPicker:v1.0.0'
}
```

## 🚀 Quick Start

### Compose Implementation (Recommended)

```kotlin
@Composable
fun MyScreen() {
    var showPicker by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    Button(onClick = { showPicker = true }) {
        Text("Select Country")
    }

    if (showPicker) {
        CountryPickerBottomSheet(
            onDismiss = { showPicker = false },
            onCountrySelected = { country ->
                selectedCountry = country
                showPicker = false
            }
        )
    }

    selectedCountry?.let {
        Text("Selected: ${it.flag} ${it.name} ${it.dialCode}")
    }
}
```

### Fragment Implementation (For legacy View-based apps)

#### Option 1: From Compose Activity

```kotlin
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreen()
        }
    }
    
    private fun showCountryPicker() {
        val picker = CountryPickerBottomSheetFragment().apply {
            // Pre-select a country (optional)
            preSelectedCountryCode = "US"
            
            // Set callback
            onCountrySelectedListener = { country ->
                Toast.makeText(
                    this@MainActivity,
                    "${country.flag} ${country.name} ${country.dialCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        picker.show(supportFragmentManager, "CountryPicker")
    }
}
```

#### Option 2: From Traditional Activity (XML-based)

```kotlin
class MyActivity : AppCompatActivity() {
    
    private lateinit var buttonSelectCountry: Button
    private lateinit var textSelectedCountry: TextView
    private var selectedCountry: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        buttonSelectCountry = findViewById(R.id.buttonSelectCountry)
        textSelectedCountry = findViewById(R.id.textSelectedCountry)
        
        buttonSelectCountry.setOnClickListener {
            showCountryPicker()
        }
    }
    
    private fun showCountryPicker() {
        val picker = CountryPickerBottomSheetFragment().apply {
            preSelectedCountryCode = selectedCountry?.code
            
            onCountrySelectedListener = { country ->
                selectedCountry = country
                textSelectedCountry.text = "${country.flag} ${country.name} ${country.dialCode}"
            }
        }
        picker.show(supportFragmentManager, "CountryPicker")
    }
}
```

#### XML Layout Example

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <Button
        android:id="@+id/buttonSelectCountry"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Select Country" />

    <TextView
        android:id="@+id/textSelectedCountry"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="No country selected"
        android:textSize="16sp" />

</LinearLayout>
```

#### Fragment with Custom Configuration

```kotlin
private fun showCountryPicker() {
    val picker = CountryPickerBottomSheetFragment().apply {
        // Set custom country list (optional)
        customCountryList = listOf(
            Country("United States", "US", "+1"),
            Country("United Kingdom", "GB", "+44"),
            Country("India", "IN", "+91"),
            Country("Canada", "CA", "+1"),
            Country("Australia", "AU", "+61")
        )
        
        // Pre-select a country
        preSelectedCountryCode = "IN"
        
        // Handle selection
        onCountrySelectedListener = { country ->
            // Handle the selected country
            println("Selected: ${country.name}")
        }
    }
    
    picker.show(supportFragmentManager, "CountryPickerFragment")
}
```

## 🎯 Filtering Countries

The library provides three powerful ways to control which countries are displayed:

### 1. Show All Countries (Default)

By default, the picker shows all 50+ built-in countries. No configuration needed!

```kotlin
// Compose
CountryPickerBottomSheet(
    onDismiss = { },
    onCountrySelected = { country -> }
    // customCountryList = null (default)
)

// Fragment
val picker = CountryPickerBottomSheetFragment()
// No customCountryList set
picker.show(supportFragmentManager, "picker")
```

### 2. Show Only Specific Countries (Whitelist)

Perfect for apps that operate in specific regions or want to limit choices.

**Use Cases:**
- Regional apps (European Union only)
- Service availability limitations
- Simplified user experience
- Compliance requirements

```kotlin
// Compose Example
val europeanCountries = listOf(
    Country("France", "FR", "+33"),
    Country("Germany", "DE", "+49"),
    Country("Italy", "IT", "+39"),
    Country("Spain", "ES", "+34"),
    Country("Netherlands", "NL", "+31")
)

CountryPickerBottomSheet(
    onDismiss = { },
    onCountrySelected = { },
    customCountryList = europeanCountries
)

// Fragment Example
val asianCountries = listOf(
    Country("India", "IN", "+91"),
    Country("China", "CN", "+86"),
    Country("Japan", "JP", "+81"),
    Country("Singapore", "SG", "+65"),
    Country("Thailand", "TH", "+66")
)

val picker = CountryPickerBottomSheetFragment().apply {
    customCountryList = asianCountries
    onCountrySelectedListener = { country -> }
}
picker.show(supportFragmentManager, "picker")
```

### 3. Block Specific Countries (Blacklist)

Show all countries except certain ones. Ideal for sanctions, legal restrictions, or service limitations.

**Use Cases:**
- Sanctions compliance
- Legal/regulatory restrictions
- Service unavailability
- Business policy enforcement

```kotlin
// Get all countries
fun getAllCountries(): List<Country> {
    return listOf(
        Country("Afghanistan", "AF", "+93"),
        Country("Albania", "AL", "+355"),
        // ... all countries
        Country("Vietnam", "VN", "+84")
    )
}

// Block specific countries
val blockedCountryCodes = setOf("KP", "IR", "SY", "CU")

val allowedCountries = getAllCountries().filter { country ->
    country.code !in blockedCountryCodes
}

// Compose
CountryPickerBottomSheet(
    onDismiss = { },
    onCountrySelected = { },
    customCountryList = allowedCountries
)

// Fragment
val picker = CountryPickerBottomSheetFragment().apply {
    customCountryList = allowedCountries
    onCountrySelectedListener = { }
}
picker.show(supportFragmentManager, "picker")
```

### Complete Fragment Demo with All Three Scenarios

```kotlin
class MyActivity : AppCompatActivity() {
    
    // Scenario 1: All countries (default)
    private fun showAllCountries() {
        val picker = CountryPickerBottomSheetFragment().apply {
            onCountrySelectedListener = { country ->
                Toast.makeText(this@MyActivity, 
                    "Selected: ${country.name}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
        picker.show(supportFragmentManager, "all_countries")
    }
    
    // Scenario 2: Only specific countries
    private fun showFewCountries() {
        val popularCountries = listOf(
            Country("United States", "US", "+1"),
            Country("United Kingdom", "GB", "+44"),
            Country("Canada", "CA", "+1"),
            Country("Australia", "AU", "+61"),
            Country("India", "IN", "+91")
        )
        
        val picker = CountryPickerBottomSheetFragment().apply {
            customCountryList = popularCountries
            onCountrySelectedListener = { country ->
                Toast.makeText(this@MyActivity, 
                    "From ${popularCountries.size} countries: ${country.name}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
        picker.show(supportFragmentManager, "few_countries")
    }
    
    // Scenario 3: Block certain countries
    private fun showWithBlockedCountries() {
        val blockedCodes = setOf("KP", "IR", "SY")
        val allCountries = getAllCountries()
        val allowedCountries = allCountries.filter { 
            it.code !in blockedCodes 
        }
        
        val picker = CountryPickerBottomSheetFragment().apply {
            customCountryList = allowedCountries
            onCountrySelectedListener = { country ->
                Toast.makeText(this@MyActivity, 
                    "Selected from ${allowedCountries.size} allowed countries", 
                    Toast.LENGTH_SHORT).show()
            }
        }
        picker.show(supportFragmentManager, "blocked_countries")
    }
}
```

## 🎨 Customization

### Basic Styling

```kotlin
CountryPickerBottomSheet(
    onDismiss = { showPicker = false },
    onCountrySelected = { country -> /* handle */ },
    style = CountryPickerStyle(
        titleText = "Choose Your Country",
        titleFontSize = 24.sp,
        titleColor = Color(0xFF6200EE),
        searchHintText = "Type to search...",
        countryNameFontSize = 18.sp
    )
)
```

### Advanced Styling

```kotlin
CountryPickerBottomSheet(
    onDismiss = { showPicker = false },
    onCountrySelected = { country -> /* handle */ },
    style = CountryPickerStyle(
        // Title
        titleText = "Select Your Country",
        titleFontSize = 22.sp,
        titleFontWeight = FontWeight.Bold,
        titleColor = Color(0xFF6200EE),
        
        // Search
        searchHintText = "Search countries...",
        searchTextSize = 16.sp,
        searchBorderColor = Color.LightGray,
        searchFocusedBorderColor = Color(0xFF6200EE),
        
        // Country Items
        countryNameFontSize = 17.sp,
        countryNameColor = Color.Black,
        flagFontSize = 28.sp,
        dialCodeFontSize = 14.sp,
        dialCodeColor = Color.Gray,
        
        // Selected Item
        selectedCountryColor = Color(0xFF6200EE),
        selectedCountryFontWeight = FontWeight.Bold,
        
        // Layout
        itemVerticalPadding = 16.dp,
        flagSpacing = 12.dp,
        dividerColor = Color.LightGray,
        
        // Animations
        enableItemAnimation = true,
        enableSearchAnimation = true,
        animationDurationMillis = 300
    )
)
```

### Pre-selection

```kotlin
CountryPickerBottomSheet(
    onDismiss = { showPicker = false },
    onCountrySelected = { country -> /* handle */ },
    preSelectedCountryCode = "US" // Highlights United States
)
```

### Custom Country List

```kotlin
val europeanCountries = listOf(
    Country("France", "FR", "+33"),
    Country("Germany", "DE", "+49"),
    Country("Italy", "IT", "+39"),
    Country("Spain", "ES", "+34")
)

CountryPickerBottomSheet(
    onDismiss = { showPicker = false },
    onCountrySelected = { country -> /* handle */ },
    customCountryList = europeanCountries
)
```

## 🎬 Animations

The library includes several built-in animations:

| Animation | Description | Control |
|-----------|-------------|---------|
| **Header Animation** | Title fades in and slides down | `enableItemAnimation` |
| **Search Animation** | Search bar expands with fade-in | `enableSearchAnimation` |
| **List Animation** | Staggered cascade effect (30ms delay) | `enableItemAnimation` |
| **Clear Button** | Scale and fade when typing | Always active |
| **Selection** | Subtle scale with spring bounce | Always active |

### Animation Control

```kotlin
// Fast animations
CountryPickerStyle(
    enableItemAnimation = true,
    enableSearchAnimation = true,
    animationDurationMillis = 150
)

// Disable all animations (better performance)
CountryPickerStyle(
    enableItemAnimation = false,
    enableSearchAnimation = false
)
```

## 📋 API Reference

### CountryPickerStyle Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `titleText` | String | "Select Country" | Title text at the top |
| `titleFontSize` | TextUnit | 22.sp | Title font size |
| `titleFontWeight` | FontWeight | Bold | Title font weight |
| `titleColor` | Color | OnSurface | Title text color |
| `searchHintText` | String | "Search country..." | Search placeholder |
| `searchTextSize` | TextUnit | 16.sp | Search text size |
| `searchBorderColor` | Color | LightGray | Unfocused border |
| `searchFocusedBorderColor` | Color | Primary | Focused border |
| `countryNameFontSize` | TextUnit | 16.sp | Country name size |
| `countryNameColor` | Color | OnSurface | Country name color |
| `flagFontSize` | TextUnit | 24.sp | Flag emoji size |
| `dialCodeFontSize` | TextUnit | 14.sp | Dial code size |
| `dialCodeColor` | Color | Gray | Dial code color |
| `selectedCountryColor` | Color | Primary | Selected item color |
| `selectedCountryFontWeight` | FontWeight | Bold | Selected item weight |
| `dividerColor` | Color | LightGray | Divider color |
| `dividerThickness` | Dp | 0.5.dp | Divider thickness |
| `itemVerticalPadding` | Dp | 16.dp | Item vertical padding |
| `flagSpacing` | Dp | 12.dp | Flag-to-name spacing |
| `enableItemAnimation` | Boolean | true | Enable list animations |
| `enableSearchAnimation` | Boolean | true | Enable search animation |
| `animationDurationMillis` | Int | 300 | Animation duration (ms) |

### Country Model

```kotlin
data class Country(
    val name: String,      // e.g., "United States"
    val code: String,      // ISO 2-letter code: "US"
    val dialCode: String   // e.g., "+1"
) {
    val flag: String       // Auto-generated emoji: 🇺🇸
}
```

### CountryPickerBottomSheet (Compose)

```kotlin
@Composable
fun CountryPickerBottomSheet(
    onDismiss: () -> Unit,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
    customCountryList: List<Country>? = null,
    preSelectedCountryCode: String? = null,
    style: CountryPickerStyle = CountryPickerStyle()
)
```

### CountryPickerBottomSheetFragment (Fragment)

```kotlin
class CountryPickerBottomSheetFragment : BottomSheetDialogFragment() {
    var onCountrySelectedListener: ((Country) -> Unit)? = null
    var customCountryList: List<Country>? = null
    var preSelectedCountryCode: String? = null
}
```

**Properties:**

| Property | Type | Description |
|----------|------|-------------|
| `onCountrySelectedListener` | `((Country) -> Unit)?` | Callback when country is selected |
| `customCountryList` | `List<Country>?` | Optional custom list of countries |
| `preSelectedCountryCode` | `String?` | ISO code of pre-selected country |

**Usage Example:**

```kotlin
val picker = CountryPickerBottomSheetFragment().apply {
    preSelectedCountryCode = "US"
    onCountrySelectedListener = { country ->
        // Handle selection
    }
}
picker.show(supportFragmentManager, "picker")
```

## 🎯 Use Cases

- 📞 Phone number input with country code selection
- 📬 Address forms requiring country selection
- 🌐 Multi-country apps with region selection
- 📦 International shipping forms
- 👤 User profile country settings
- 🛂 Travel booking applications
- 💳 Payment processing forms
- 🏢 **Legacy app migration** - Use Fragment while migrating to Compose
- 📱 **Hybrid apps** - Mix Compose and XML views

## 🔄 Migration Guide

### From XML to Compose

If you're migrating from XML to Compose, you can use both implementations side-by-side:

```kotlin
class HybridActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Option 1: Use Fragment (legacy)
        showFragmentPicker()
        
        // Option 2: Use Compose (modern)
        setContent {
            CountryPickerBottomSheet(/* ... */)
        }
    }
    
    private fun showFragmentPicker() {
        CountryPickerBottomSheetFragment().apply {
            onCountrySelectedListener = { /* handle */ }
        }.show(supportFragmentManager, "picker")
    }
}
```

## 🔧 Requirements

- **Android**: 7.0 (API 24) or higher
- **Jetpack Compose**: 1.5.0 or higher
- **Material Design 3**: Required
- **Kotlin**: 1.9.0 or higher

## 📸 Screenshots

> Add your screenshots here when available

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct.

## 📝 Changelog

See [CHANGELOG.md](CHANGELOG.md) for a detailed list of changes.

## 📄 License

```
MIT License

Copyright (c) 2025 Jaypatelbond

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👤 Author

**Jaypatelbond**
- GitHub: [@jaypatelbond](https://github.com/jaypatelbond)

## ⭐ Show Your Support

Give a ⭐️ if this project helped you!

## 🙏 Acknowledgments

- Inspired by modern country picker implementations
- Built with ❤️ using Jetpack Compose
- Thanks to all contributors

---

**Made with ❤️ for the Android community**
