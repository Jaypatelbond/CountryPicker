# 🌍 Country Picker Library for Android

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.5.0-blue.svg)](https://developer.android.com/jetpack/compose)
[![](https://jitpack.io/v/Jaypatelbond/CountryPicker.svg)](https://jitpack.io/#Jaypatelbond/CountryPicker)

A beautiful, fully customizable country picker component for Android with Jetpack Compose support.

<p align="center">
  <img src="images/image_1.png" width="30%" />
  <img src="images/image_2.png" width="30%" />
  <img src="images/image_3.png" width="30%" />
</p>


## ✨ Features

- 🎨 **Fully Customizable** - Colors, fonts, shapes.
- 🔍 **Smart Search** - Filter by name, code, or dial code.
- 🌍 **50+ Countries** - Built-in list with flags.
- 📱 **Material Design 3** - Modern UI.
- ⚡ **Lightweight** - Pure Kotlin & Compose.

## 📦 Installation

Add to your module's `build.gradle.kts`:

```kotlin
dependencies {
    // For Jetpack Compose
    implementation("com.github.Jaypatelbond.CountryPicker:compose:v1.1.0")

    // For Views / Fragments
    implementation("com.github.Jaypatelbond.CountryPicker:view:v1.1.0")
    
    // Core (Data only)
    implementation("com.github.Jaypatelbond.CountryPicker:core:v1.1.0")
}
```

## 🚀 Quick Start (Compose)

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
}
```

## 🎨 Customization

Customize the look and feel easily:

```kotlin
CountryPickerBottomSheet(
    onDismiss = { showPicker = false },
    onCountrySelected = { /* ... */ },
    style = CountryPickerStyle(
        titleText = "Select Location",
        titleColor = Color(0xFF6200EE),
        searchHintText = "Search...",
        enableItemAnimation = true
    )
)
```

## 🎯 Filtering

You can show all countries, a specific list, or block certain ones.

```kotlin
// Show only specific countries
val europeanCountries = listOf(
    Country("France", "FR", "+33"),
    Country("Germany", "DE", "+49")
)

CountryPickerBottomSheet(
    // ...
    customCountryList = europeanCountries
)
```

## 📱 Legacy Support (Fragments/Views)

<details>
<summary>Click to expand Fragment implementation details</summary>

### From Activity/Fragment

```kotlin
val picker = CountryPickerBottomSheetFragment().apply {
    onCountrySelectedListener = { country ->
        Toast.makeText(context, "Selected: ${country.name}", Toast.LENGTH_SHORT).show()
    }
}
picker.show(supportFragmentManager, "CountryPicker")
```

</details>

## 🤝 Contributing

Contributions are welcome! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📄 License

MIT License. See [LICENSE](LICENSE) for details.
