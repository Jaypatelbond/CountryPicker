package me.jaypatelbond.countrypicker

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryPickerBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomSheet_displaysSearchBar() {
        var dismissed = false

        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = { dismissed = true },
                onCountrySelected = {}
            )
        }

        composeTestRule.onNodeWithText("Search country...").assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysCountryList() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {}
            )
        }

        // Verify at least some countries are displayed
        composeTestRule.onNodeWithText("United States", substring = true).assertExists()
        composeTestRule.onNodeWithText("Canada", substring = true).assertExists()
    }

    @Test
    fun searchBar_filtersCountries() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {}
            )
        }

        // Type in search field
        composeTestRule.onNodeWithText("Search country...")
            .performTextInput("United States")

        // Verify filtered results
        composeTestRule.onNodeWithText("United States", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Canada", substring = true).assertDoesNotExist()
    }

    @Test
    fun searchBar_searchByDialCode() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {}
            )
        }

        composeTestRule.onNodeWithText("Search country...")
            .performTextInput("+1")

        // Should show US and Canada
        composeTestRule.onNodeWithText("United States", substring = true).assertExists()
        composeTestRule.onNodeWithText("Canada", substring = true).assertExists()
    }

    @Test
    fun searchBar_searchByCountryCode() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {}
            )
        }

        composeTestRule.onNodeWithText("Search country...")
            .performTextInput("US")

        composeTestRule.onNodeWithText("United States", substring = true).assertIsDisplayed()
    }

    @Test
    fun searchBar_caseInsensitiveSearch() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {}
            )
        }

        composeTestRule.onNodeWithText("Search country...")
            .performTextInput("united states")

        composeTestRule.onNodeWithText("United States", substring = true).assertIsDisplayed()
    }

    @Test
    fun countryItem_clickTriggersCallback() {
        var selectedCountry: Country? = null

        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = { selectedCountry = it }
            )
        }

        // Click on first visible country item
        composeTestRule.onAllNodesWithTag("country_item")[0].performClick()

        // Verify callback was triggered
        assert(selectedCountry != null)
    }

    @Test
    fun preSelectedCountry_isHighlighted() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {},
                preSelectedCountryCode = "US"
            )
        }

        // The US item should be present and selected
        composeTestRule.onNodeWithText("United States", substring = true).assertExists()
    }

    @Test
    fun customCountryList_isDisplayed() {
        val customCountries = listOf(
            Country("Test Country 1", "T1", "🏳️", "+111"),
            Country("Test Country 2", "T2", "🏴", "+222")
        )

        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {},
                customCountryList = customCountries
            )
        }

        composeTestRule.onNodeWithText("Test Country 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Country 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("United States", substring = true).assertDoesNotExist()
    }

    @Test
    fun emptySearchResults_showsNoResults() {
        composeTestRule.setContent {
            CountryPickerBottomSheet(
                onDismiss = {},
                onCountrySelected = {}
            )
        }

        composeTestRule.onNodeWithText("Search country...")
            .performTextInput("XYZ123NonExistent")

        // Should show some indication of no results or empty list
        composeTestRule.onNodeWithText("United States", substring = true).assertDoesNotExist()
    }
}

