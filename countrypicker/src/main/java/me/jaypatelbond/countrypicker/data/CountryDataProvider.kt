package me.jaypatelbond.countrypicker.data

import me.jaypatelbond.countrypicker.model.Country


object CountryDataProvider {
    
    fun getAllCountries(): List<Country> {
        return listOf(
            Country("United States", "US", "+1"),
            Country("Canada", "CA", "+1"),
            Country("United Kingdom", "GB", "+44"),
            Country("Australia", "AU", "+61"),
            Country("India", "IN", "+91"),
            Country("China", "CN", "+86"),
            Country("Japan", "JP", "+81"),
            Country("Germany", "DE", "+49"),
            Country("France", "FR", "+33"),
            Country("Italy", "IT", "+39"),
            Country("Spain", "ES", "+34"),
            Country("Mexico", "MX", "+52"),
            Country("Brazil", "BR", "+55"),
            Country("Russia", "RU", "+7"),
            Country("South Korea", "KR", "+82"),
            Country("Indonesia", "ID", "+62"),
            Country("Turkey", "TR", "+90"),
            Country("Saudi Arabia", "SA", "+966"),
            Country("UAE", "AE", "+971"),
            Country("Singapore", "SG", "+65"),
            Country("Malaysia", "MY", "+60"),
            Country("Thailand", "TH", "+66"),
            Country("Vietnam", "VN", "+84"),
            Country("Philippines", "PH", "+63"),
            Country("Pakistan", "PK", "+92"),
            Country("Bangladesh", "BD", "+880"),
            Country("Egypt", "EG", "+20"),
            Country("South Africa", "ZA", "+27"),
            Country("Nigeria", "NG", "+234"),
            Country("Argentina", "AR", "+54"),
            Country("Colombia", "CO", "+57"),
            Country("Chile", "CL", "+56"),
            Country("Peru", "PE", "+51"),
            Country("Poland", "PL", "+48"),
            Country("Netherlands", "NL", "+31"),
            Country("Belgium", "BE", "+32"),
            Country("Sweden", "SE", "+46"),
            Country("Norway", "NO", "+47"),
            Country("Denmark", "DK", "+45"),
            Country("Finland", "FI", "+358"),
            Country("Switzerland", "CH", "+41"),
            Country("Austria", "AT", "+43"),
            Country("Portugal", "PT", "+351"),
            Country("Greece", "GR", "+30"),
            Country("Czech Republic", "CZ", "+420"),
            Country("Romania", "RO", "+40"),
            Country("Hungary", "HU", "+36"),
            Country("Ukraine", "UA", "+380"),
            Country("New Zealand", "NZ", "+64"),
            Country("Ireland", "IE", "+353")
        ).sortedBy { it.name }
    }
}
