package me.jaypatelbond.countrypicker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Data class for customizing the appearance and behavior of the Country Picker.
 *
 * All parameters have sensible defaults, so you only need to specify the ones you want to customize.
 *
 * @property titleText The text displayed at the top of the bottom sheet. Default: "Select Country"
 * @property titleFontSize Font size for the title. Default: 22.sp
 * @property titleFontWeight Font weight for the title. Default: FontWeight.Bold
 * @property titleColor Color of the title text. Default: MaterialTheme.colorScheme.onSurface
 * @property titleFontFamily Custom font family for the title. Default: null (system default)
 *
 * @property searchHintText Placeholder text in the search bar. Default: "Search country..."
 * @property searchTextSize Font size for search input text. Default: 16.sp
 * @property searchTextColor Color of the search input text. Default: MaterialTheme.colorScheme.onSurface
 * @property searchFontFamily Custom font family for search text. Default: null
 * @property searchBackgroundColor Background color of the search field. Default: Color.Transparent
 * @property searchBorderColor Border color when search is not focused. Default: Color.LightGray
 * @property searchFocusedBorderColor Border color when search is focused. Default: MaterialTheme.colorScheme.primary
 *
 * @property countryNameFontSize Font size for country names. Default: 16.sp
 * @property countryNameColor Color of country names. Default: MaterialTheme.colorScheme.onSurface
 * @property countryNameFontFamily Custom font family for country names. Default: null
 * @property countryNameFontWeight Font weight for country names. Default: FontWeight.Normal
 *
 * @property flagFontSize Font size for flag emojis. Default: 24.sp
 *
 * @property dialCodeFontSize Font size for dial codes. Default: 14.sp
 * @property dialCodeColor Color of dial codes. Default: Color.Gray
 * @property dialCodeFontFamily Custom font family for dial codes. Default: null
 *
 * @property selectedCountryColor Color for the selected country text. Default: MaterialTheme.colorScheme.primary
 * @property selectedCountryFontWeight Font weight for selected country. Default: FontWeight.Bold
 *
 * @property dividerColor Color of the divider between items. Default: Color.LightGray
 * @property dividerThickness Thickness of the divider. Default: 0.5.dp
 *
 * @property itemVerticalPadding Vertical padding for each country item. Default: 16.dp
 * @property itemHorizontalPadding Horizontal padding for each country item. Default: 0.dp
 * @property flagSpacing Space between flag emoji and country name. Default: 12.dp
 *
 * @property enableItemAnimation Enable fade-in and slide animations for list items. Default: true
 * @property enableSearchAnimation Enable animations for search bar appearance. Default: true
 * @property animationDurationMillis Duration of animations in milliseconds. Default: 300ms
 */
data class CountryPickerStyle(
    // Title customization
    val titleText: String = "Select Country",
    val titleFontSize: TextUnit = 22.sp,
    val titleFontWeight: FontWeight = FontWeight.Bold,
    val titleColor: Color = Color.Unspecified,
    val titleFontFamily: FontFamily? = null,

    // Search customization
    val searchHintText: String = "Search country...",
    val searchTextSize: TextUnit = 16.sp,
    val searchTextColor: Color = Color.Unspecified,
    val searchFontFamily: FontFamily? = null,
    val searchBackgroundColor: Color = Color.Transparent,
    val searchBorderColor: Color = Color.LightGray,
    val searchFocusedBorderColor: Color = Color.Unspecified,

    // Country item customization
    val countryNameFontSize: TextUnit = 16.sp,
    val countryNameColor: Color = Color.Unspecified,
    val countryNameFontFamily: FontFamily? = null,
    val countryNameFontWeight: FontWeight = FontWeight.Normal,

    val flagFontSize: TextUnit = 24.sp,

    val dialCodeFontSize: TextUnit = 14.sp,
    val dialCodeColor: Color = Color.Gray,
    val dialCodeFontFamily: FontFamily? = null,

    // Selected item customization
    val selectedCountryColor: Color = Color.Unspecified,
    val selectedCountryFontWeight: FontWeight = FontWeight.Bold,

    // Divider customization
    val dividerColor: Color = Color.LightGray,
    val dividerThickness: Dp = 0.5.dp,

    // Padding and spacing
    val itemVerticalPadding: Dp = 16.dp,
    val itemHorizontalPadding: Dp = 0.dp,
    val flagSpacing: Dp = 12.dp,

    // Animation
    val enableItemAnimation: Boolean = true,
    val enableSearchAnimation: Boolean = true,
    val animationDurationMillis: Int = 300
)
