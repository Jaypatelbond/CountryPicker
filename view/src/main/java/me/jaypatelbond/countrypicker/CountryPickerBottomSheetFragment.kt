package me.jaypatelbond.countrypicker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.jaypatelbond.countrypicker.adapter.CountryAdapter
import me.jaypatelbond.countrypicker.model.Country

/**
 * A BottomSheetDialogFragment implementation for country selection.
 *
 * This fragment displays a list of countries with their flags and dial codes in a bottom sheet dialog.
 * It's designed for traditional Android View-based applications.
 *
 * ## Features
 * - Search/filter countries in real-time
 * - Displays country flag, name, and dial code
 * - Supports custom country lists
 * - Pre-selection highlighting
 * - Automatically expands to full height
 * - Material Design 3 theming support
 *
 * ## Usage Example
 * ```kotlin
 * val countryPicker = CountryPickerBottomSheetFragment().apply {
 *     customCountryList = listOf(
 *         Country("United States", "US", "+1"),
 *         Country("India", "IN", "+91")
 *     )
 *     preSelectedCountryCode = "US"
 *     onCountrySelectedListener = { country ->
 *         Log.d("Country", "Selected: ${country.name} ${country.dialCode}")
 *     }
 * }
 * countryPicker.show(supportFragmentManager, "CountryPicker")
 * ```
 *
 * @see CountryPickerBottomSheet For the pure Compose implementation
 * @author Jaypatelbond
 */
class CountryPickerBottomSheetFragment : BottomSheetDialogFragment() {

    /**
     * Callback listener invoked when a country is selected.
     */
    var onCountrySelectedListener: ((Country) -> Unit)? = null

    /**
     * Optional custom list of countries to display.
     * If null, all built-in countries will be shown.
     */
    var customCountryList: List<Country>? = null

    /**
     * ISO 2-letter country code to pre-select and highlight in the list.
     * Example: "US", "IN", "GB"
     */
    var preSelectedCountryCode: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var titleTextView: TextView
    private lateinit var adapter: CountryAdapter
    private var allCountries: List<Country> = emptyList()
    private var filteredCountries: List<Country> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, me.jaypatelbond.countrypicker.view.R.style.BottomSheetDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        // Make bottom sheet full screen
        val dialog = dialog as? BottomSheetDialog
        dialog?.let {
            val bottomSheet = it.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
                behavior.isDraggable = true

                // Set peek height to match parent height for full screen effect
                val layoutParams = sheet.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                sheet.layoutParams = layoutParams
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(me.jaypatelbond.countrypicker.view.R.layout.fragment_country_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        titleTextView = view.findViewById(me.jaypatelbond.countrypicker.view.R.id.titleTextView)
        searchEditText = view.findViewById(me.jaypatelbond.countrypicker.view.R.id.searchEditText)
        recyclerView = view.findViewById(me.jaypatelbond.countrypicker.view.R.id.recyclerView)

        // Setup countries list - use custom list if provided, otherwise use all countries
        allCountries = customCountryList ?: getAllCountries()
        filteredCountries = allCountries

        // Setup RecyclerView
        setupRecyclerView()

        // Setup search functionality
        setupSearch()

        // Pre-select country if specified
        preSelectedCountryCode?.let { code ->
            scrollToCountry(code)
        }
    }

    private fun setupRecyclerView() {
        adapter = CountryAdapter(
            countries = filteredCountries,
            preSelectedCountryCode = preSelectedCountryCode,
            onCountryClick = { country ->
                onCountrySelectedListener?.invoke(country)
                dismiss()
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CountryPickerBottomSheetFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCountries(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterCountries(query: String) {
        filteredCountries = if (query.isEmpty()) {
            allCountries
        } else {
            allCountries.filter { country ->
                country.name.contains(query, ignoreCase = true) ||
                        country.code.contains(query, ignoreCase = true) ||
                        country.dialCode.contains(query, ignoreCase = true)
            }
        }
        adapter.updateCountries(filteredCountries)
    }

    private fun scrollToCountry(countryCode: String) {
        val position = filteredCountries.indexOfFirst { it.code == countryCode }
        if (position != -1) {
            recyclerView.post {
                recyclerView.scrollToPosition(position)
            }
        }
    }

    /**
     * Returns the complete list of all available countries.
     */
    private fun getAllCountries(): List<Country> = listOf(
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
