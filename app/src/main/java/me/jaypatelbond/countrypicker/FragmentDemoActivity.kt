package me.jaypatelbond.countrypicker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.jaypatelbond.countrypicker.model.Country

/**
 * Example Activity demonstrating Fragment-based Country Picker usage
 * in a traditional XML-based Android app (without Compose).
 *
 * This demo shows three scenarios:
 * 1. All countries (default behavior)
 * 2. Only specific countries (whitelist)
 * 3. Block certain countries (blacklist)
 */
class FragmentDemoActivity : AppCompatActivity() {

    private lateinit var buttonAllCountries: Button
    private lateinit var buttonFewCountries: Button
    private lateinit var buttonBlockCountries: Button
    private lateinit var textSelectedCountry: TextView
    private var selectedCountry: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_demo)

        // Initialize views
        buttonAllCountries = findViewById(R.id.buttonAllCountries)
        buttonFewCountries = findViewById(R.id.buttonFewCountries)
        buttonBlockCountries = findViewById(R.id.buttonBlockCountries)
        textSelectedCountry = findViewById(R.id.textSelectedCountry)

        // Set click listeners for different scenarios
        buttonAllCountries.setOnClickListener {
            showAllCountriesPicker()
        }

        buttonFewCountries.setOnClickListener {
            showFewCountriesPicker()
        }

        buttonBlockCountries.setOnClickListener {
            showBlockedCountriesPicker()
        }

        // Restore selected country if available
        savedInstanceState?.let {
            val countryName = it.getString("country_name")
            val countryCode = it.getString("country_code")
            val dialCode = it.getString("dial_code")

            if (countryName != null && countryCode != null && dialCode != null) {
                selectedCountry = Country(countryName, countryCode, dialCode)
                updateUI()
            }
        }
    }

    /**
     * Scenario 1: Shows ALL available countries (default behavior).
     * This displays the complete built-in list of 50+ countries.
     */
    private fun showAllCountriesPicker() {
        val picker = CountryPickerBottomSheetFragment().apply {
            // No customCountryList = shows all countries by default
            preSelectedCountryCode = selectedCountry?.code

            onCountrySelectedListener = { country ->
                selectedCountry = country
                updateUI("All Countries Mode")
            }
        }

        picker.show(supportFragmentManager, "AllCountriesPicker")
    }

    /**
     * Scenario 2: Shows ONLY specific countries (whitelist approach).
     * Useful for apps that operate in specific regions only.
     *
     * Examples:
     * - European-only service
     * - North American markets
     * - ASEAN countries
     */
    private fun showFewCountriesPicker() {
        // Example: Only show major English-speaking countries
        val selectedCountries = listOf(
            Country("United States", "US", "+1"),
            Country("United Kingdom", "GB", "+44"),
            Country("Canada", "CA", "+1"),
            Country("Australia", "AU", "+61"),
            Country("New Zealand", "NZ", "+64"),
            Country("Ireland", "IE", "+353"),
            Country("South Africa", "ZA", "+27")
        )

        val picker = CountryPickerBottomSheetFragment().apply {
            customCountryList = selectedCountries // Only these countries will show
            preSelectedCountryCode = selectedCountry?.code

            onCountrySelectedListener = { country ->
                selectedCountry = country
                updateUI("Few Countries Mode (7 countries)")
            }
        }

        picker.show(supportFragmentManager, "FewCountriesPicker")
    }

    /**
     * Scenario 3: Shows all countries EXCEPT blocked ones (blacklist approach).
     * Useful for compliance, sanctions, or regional restrictions.
     *
     * Examples:
     * - Exclude sanctioned countries
     * - Remove countries where service isn't available
     * - Exclude for legal/regulatory reasons
     */
    private fun showBlockedCountriesPicker() {
        // Countries to block (example)
        val blockedCountryCodes = setOf("KP", "IR", "SY", "CU", "VE")

        // Get all countries and filter out blocked ones
        val allowedCountries = getAllCountries().filter { country ->
            country.code !in blockedCountryCodes
        }

        val picker = CountryPickerBottomSheetFragment().apply {
            customCountryList = allowedCountries
            preSelectedCountryCode = selectedCountry?.code

            onCountrySelectedListener = { country ->
                selectedCountry = country
                updateUI("Blocked Countries Mode (${blockedCountryCodes.size} countries blocked)")
            }
        }

        picker.show(supportFragmentManager, "BlockedCountriesPicker")
    }

    /**
     * Updates the UI with the selected country information.
     */
    private fun updateUI(mode: String = "") {
        selectedCountry?.let { country ->
            textSelectedCountry.text = buildString {
                if (mode.isNotEmpty()) {
                    append("$mode\n\n")
                }
                append("Selected Country:\n")
                append("${country.flag} ${country.name}\n")
                append("Code: ${country.code}\n")
                append("Dial Code: ${country.dialCode}")
            }
        } ?: run {
            textSelectedCountry.text = "No country selected\n\nTry one of the options above!"
        }
    }

    /**
     * Returns the complete list of all available countries.
     * This is the same list used internally by the library.
     */
    private fun getAllCountries(): List<Country> {
        return listOf(
            Country("Afghanistan", "AF", "+93"),
            Country("Albania", "AL", "+355"),
            Country("Algeria", "DZ", "+213"),
            Country("Argentina", "AR", "+54"),
            Country("Australia", "AU", "+61"),
            Country("Austria", "AT", "+43"),
            Country("Bangladesh", "BD", "+880"),
            Country("Belgium", "BE", "+32"),
            Country("Brazil", "BR", "+55"),
            Country("Canada", "CA", "+1"),
            Country("Chile", "CL", "+56"),
            Country("China", "CN", "+86"),
            Country("Colombia", "CO", "+57"),
            Country("Cuba", "CU", "+53"),
            Country("Czech Republic", "CZ", "+420"),
            Country("Denmark", "DK", "+45"),
            Country("Egypt", "EG", "+20"),
            Country("Finland", "FI", "+358"),
            Country("France", "FR", "+33"),
            Country("Germany", "DE", "+49"),
            Country("Greece", "GR", "+30"),
            Country("Hong Kong", "HK", "+852"),
            Country("Hungary", "HU", "+36"),
            Country("Iceland", "IS", "+354"),
            Country("India", "IN", "+91"),
            Country("Indonesia", "ID", "+62"),
            Country("Iran", "IR", "+98"),
            Country("Iraq", "IQ", "+964"),
            Country("Ireland", "IE", "+353"),
            Country("Israel", "IL", "+972"),
            Country("Italy", "IT", "+39"),
            Country("Japan", "JP", "+81"),
            Country("Kenya", "KE", "+254"),
            Country("Malaysia", "MY", "+60"),
            Country("Mexico", "MX", "+52"),
            Country("Netherlands", "NL", "+31"),
            Country("New Zealand", "NZ", "+64"),
            Country("Nigeria", "NG", "+234"),
            Country("North Korea", "KP", "+850"),
            Country("Norway", "NO", "+47"),
            Country("Pakistan", "PK", "+92"),
            Country("Philippines", "PH", "+63"),
            Country("Poland", "PL", "+48"),
            Country("Portugal", "PT", "+351"),
            Country("Russia", "RU", "+7"),
            Country("Saudi Arabia", "SA", "+966"),
            Country("Singapore", "SG", "+65"),
            Country("South Africa", "ZA", "+27"),
            Country("South Korea", "KR", "+82"),
            Country("Spain", "ES", "+34"),
            Country("Sweden", "SE", "+46"),
            Country("Switzerland", "CH", "+41"),
            Country("Syria", "SY", "+963"),
            Country("Taiwan", "TW", "+886"),
            Country("Thailand", "TH", "+66"),
            Country("Turkey", "TR", "+90"),
            Country("Ukraine", "UA", "+380"),
            Country("United Arab Emirates", "AE", "+971"),
            Country("United Kingdom", "GB", "+44"),
            Country("United States", "US", "+1"),
            Country("Venezuela", "VE", "+58"),
            Country("Vietnam", "VN", "+84")
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedCountry?.let {
            outState.putString("country_name", it.name)
            outState.putString("country_code", it.code)
            outState.putString("dial_code", it.dialCode)
        }
    }
}
