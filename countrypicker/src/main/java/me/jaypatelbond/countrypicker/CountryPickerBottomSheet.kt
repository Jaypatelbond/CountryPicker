package me.jaypatelbond.countrypicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.jaypatelbond.countrypicker.data.CountryDataProvider
import me.jaypatelbond.countrypicker.model.Country
import java.util.Locale

/**
 * Country Picker Bottom Sheet Library
 *
 * A fully customizable country picker component for Jetpack Compose with search functionality,
 * animations, and extensive styling options.
 *
 * ## Key Features
 * - 🎨 Fully customizable UI with [CountryPickerStyle]
 * - 🔍 Real-time search with country name, code, and dial code filtering
 * - ✨ Smooth animations for list items and search bar
 * - 🌍 Support for all countries with flag emojis
 * - 📱 Material Design 3 theming
 * - 🎯 Pre-selection support with visual highlighting
 * - 🔧 Custom country list support
 *
 * ## Animations Included
 *
 * ### 1. Header Animation
 * The title slides down with a fade-in effect when the bottom sheet opens.
 *
 * ### 2. Search Bar Animation
 * The search bar expands vertically with a 100ms delay for a polished appearance.
 *
 * ### 3. Staggered List Animation
 * Country items appear sequentially with a cascading wave effect:
 * - Each item fades in
 * - Each item slides in from the left (-40px)
 * - 30ms delay between each item creates a stagger effect
 *
 * ### 4. Clear Button Animation
 * The "X" button in the search bar scales in/out with fade effects when typing.
 *
 * ### 5. Selection Scale Animation
 * Selected countries subtly scale up (2%) with a spring-bouncy animation.
 *
 * ## Animation Control
 * ```kotlin
 * CountryPickerStyle(
 *     enableItemAnimation = true,        // Toggle list animations
 *     enableSearchAnimation = true,      // Toggle search animations
 *     animationDurationMillis = 300      // Control speed
 * )
 * ```
 *
 * ## Basic Usage
 * ```kotlin
 * var showPicker by remember { mutableStateOf(false) }
 *
 * if (showPicker) {
 *     CountryPickerBottomSheet(
 *         onDismiss = { showPicker = false },
 *         onCountrySelected = { country ->
 *             println("Selected: ${country.name} ${country.dialCode}")
 *         }
 *     )
 * }
 * ```
 *
 * ## Advanced Usage with Custom Styling
 * ```kotlin
 * CountryPickerBottomSheet(
 *     onDismiss = { showPicker = false },
 *     onCountrySelected = { country -> /* handle selection */ },
 *     preSelectedCountryCode = "US",
 *     style = CountryPickerStyle(
 *         titleText = "Choose Your Country",
 *         titleColor = Color(0xFF6200EE),
 *         titleFontSize = 24.sp,
 *         searchHintText = "Type to search...",
 *         countryNameFontSize = 18.sp,
 *         enableItemAnimation = true,
 *         animationDurationMillis = 400
 *     )
 * )
 * ```
 *
 * ## Disable Animations for Performance
 * ```kotlin
 * CountryPickerBottomSheet(
 *     style = CountryPickerStyle(
 *         enableItemAnimation = false,
 *         enableSearchAnimation = false
 *     )
 * )
 * ```
 *
 * @author Jaypatelbond
 * @version 1.0.0
 * @see CountryPickerStyle for all customization options
 * @see CountryPickerBottomSheetFragment for Fragment-based implementation
 */

/**
 * A Material 3 bottom sheet that displays a searchable list of countries with their flags and dial codes.
 * Supports edge-to-edge display and custom styling.
 *
 * @param onDismiss Callback invoked when the bottom sheet is dismissed
 * @param onCountrySelected Callback invoked when a country is selected
 * @param modifier Modifier to be applied to the bottom sheet
 * @param customCountryList Optional custom list of countries to display. If null, uses default list
 * @param preSelectedCountryCode Optional country code to pre-select in the list
 * @param style Custom styling options for the country picker
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerBottomSheet(
    onDismiss: () -> Unit,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
    customCountryList: List<Country>? = null,
    preSelectedCountryCode: String? = null,
    style: CountryPickerStyle = CountryPickerStyle()
) {
    val allCountries = remember { customCountryList ?: CountryDataProvider.getAllCountries() }
    var searchQuery by remember { mutableStateOf("") }

    val filteredCountries by remember(allCountries) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                allCountries
            } else {
                val query = searchQuery.lowercase(Locale.ROOT)
                allCountries.filter {
                    it.name.lowercase(Locale.ROOT).contains(query) ||
                            it.code.lowercase(Locale.ROOT).contains(query) ||
                            it.dialCode.contains(query)
                }
            }
        }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
        containerColor = MaterialTheme.colorScheme.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)

        ) {
            // Header
            AnimatedVisibility(
                visible = true,
                enter = if (style.enableItemAnimation) {
                    fadeIn(animationSpec = tween(style.animationDurationMillis)) +
                            slideInVertically(animationSpec = tween(style.animationDurationMillis))
                } else {
                    EnterTransition.None
                }
            ) {
                CountryPickerHeader(
                    titleText = style.titleText,
                    titleFontSize = style.titleFontSize,
                    titleFontWeight = style.titleFontWeight,
                    titleColor = style.titleColor,
                    titleFontFamily = style.titleFontFamily,
                    onDismiss = onDismiss
                )
            }

            // Search Bar
            AnimatedVisibility(
                visible = true,
                enter = if (style.enableSearchAnimation) {
                    fadeIn(
                        animationSpec = tween(
                            style.animationDurationMillis,
                            delayMillis = 100
                        )
                    ) +
                            expandVertically(
                                animationSpec = tween(
                                    style.animationDurationMillis,
                                    delayMillis = 100
                                )
                            )
                } else {
                    EnterTransition.None
                }
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    style = style,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            // Country List
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = filteredCountries,
                    key = { it.code }
                ) { country ->
                    val index = filteredCountries.indexOf(country)
                    AnimatedVisibility(
                        visible = true,
                        enter = if (style.enableItemAnimation) {
                            fadeIn(
                                animationSpec = tween(
                                    durationMillis = style.animationDurationMillis,
                                    delayMillis = index * 30
                                )
                            ) + slideInHorizontally(
                                animationSpec = tween(
                                    durationMillis = style.animationDurationMillis,
                                    delayMillis = index * 30
                                ),
                                initialOffsetX = { -40 }
                            )
                        } else {
                            EnterTransition.None
                        }
                    ) {
                        CountryItem(
                            country = country,
                            isSelected = country.code == preSelectedCountryCode,
                            style = style,
                            onClick = {
                                onCountrySelected(country)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Header component with title and close button.
 */
@Composable
private fun CountryPickerHeader(
    titleText: String,
    titleFontSize: TextUnit,
    titleFontWeight: FontWeight,
    titleColor: Color,
    titleFontFamily: FontFamily?,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Centered title
        Text(
            text = titleText,
            fontSize = titleFontSize,
            fontWeight = titleFontWeight,
            color = if (titleColor == Color.Unspecified) {
                MaterialTheme.colorScheme.onSurface
            } else {
                titleColor
            },
            fontFamily = titleFontFamily,
            modifier = Modifier.align(Alignment.Center)
        )

        // Close button on the right
        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close picker"
            )
        }
    }
}


/**
 * Search bar component with animated clear button.
 */
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    style: CountryPickerStyle,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = style.searchHintText,
                fontSize = style.searchTextSize,
                fontFamily = style.searchFontFamily
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        textStyle = TextStyle(
            fontSize = style.searchTextSize,
            color = if (style.searchTextColor == Color.Unspecified) {
                MaterialTheme.colorScheme.onSurface
            } else {
                style.searchTextColor
            },
            fontFamily = style.searchFontFamily
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (style.searchFocusedBorderColor == Color.Unspecified) {
                MaterialTheme.colorScheme.primary
            } else {
                style.searchFocusedBorderColor
            },
            unfocusedBorderColor = style.searchBorderColor,
            focusedContainerColor = style.searchBackgroundColor,
            unfocusedContainerColor = style.searchBackgroundColor
        )
    )
}


/**
 * Individual country item with selection animation.
 */
@Composable
private fun CountryItem(
    country: Country,
    isSelected: Boolean,
    style: CountryPickerStyle,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "country_item_scale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = style.itemVerticalPadding,
                    horizontal = style.itemHorizontalPadding
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = country.flag,
                    fontSize = style.flagFontSize,
                    modifier = Modifier.padding(end = style.flagSpacing)
                )
                Text(
                    text = country.name,
                    fontSize = style.countryNameFontSize,
                    color = if (isSelected) {
                        if (style.selectedCountryColor == Color.Unspecified) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            style.selectedCountryColor
                        }
                    } else {
                        if (style.countryNameColor == Color.Unspecified) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            style.countryNameColor
                        }
                    },
                    fontWeight = if (isSelected) {
                        style.selectedCountryFontWeight
                    } else {
                        style.countryNameFontWeight
                    },
                    fontFamily = style.countryNameFontFamily
                )
            }

            Text(
                text = country.dialCode,
                fontSize = style.dialCodeFontSize,
                color = if (isSelected) {
                    if (style.selectedCountryColor == Color.Unspecified) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        style.selectedCountryColor
                    }
                } else {
                    style.dialCodeColor
                },
                fontFamily = style.dialCodeFontFamily,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        HorizontalDivider(
            thickness = style.dividerThickness,
            color = style.dividerColor.copy(alpha = 0.5f)
        )
    }
}
