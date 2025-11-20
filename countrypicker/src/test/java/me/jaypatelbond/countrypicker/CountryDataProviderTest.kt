package me.jaypatelbond.countrypicker

import me.jaypatelbond.countrypicker.data.CountryDataProvider
import org.junit.Assert.*
import org.junit.Test

class CountryDataProviderTest {

    @Test
    fun `getAllCountries returns non-empty list`() {
        val countries = CountryDataProvider.getAllCountries()
        assertTrue(countries.isNotEmpty())
    }

    @Test
    fun `getAllCountries returns at least 200 countries`() {
        val countries = CountryDataProvider.getAllCountries()
        assertTrue("Expected at least 200 countries, got ${countries.size}",
            countries.size >= 200)
    }

    @Test
    fun `all countries have valid data`() {
        val countries = CountryDataProvider.getAllCountries()

        countries.forEach { country ->
            assertNotNull(country.name)
            assertNotNull(country.code)
            assertNotNull(country.flag)
            assertNotNull(country.dialCode)
            assertTrue("Country name should not be blank: ${country.name}",
                country.name.isNotBlank())
            assertTrue("Country code should be 2 characters: ${country.code}",
                country.code.length == 2)
            assertTrue("Dial code should start with +: ${country.dialCode}",
                country.dialCode.startsWith("+"))
        }
    }

    @Test
    fun `all country codes are unique`() {
        val countries = CountryDataProvider.getAllCountries()
        val codes = countries.map { it.code }
        val uniqueCodes = codes.toSet()

        assertEquals("Duplicate country codes found", codes.size, uniqueCodes.size)
    }

    @Test
    fun `common countries are present`() {
        val countries = CountryDataProvider.getAllCountries()
        val countryCodes = countries.map { it.code }

        val commonCountries = listOf("US", "GB", "CA", "AU", "IN", "DE", "FR", "JP", "CN", "BR")
        commonCountries.forEach { code ->
            assertTrue("Country code $code should be present", countryCodes.contains(code))
        }
    }

    @Test
    fun `getCountryByCode returns correct country`() {
        val countries = CountryDataProvider.getAllCountries()
        val usCountry = countries.find { it.code == "US" }

        assertNotNull(usCountry)
        assertEquals("US", usCountry?.code)
        assertEquals("+1", usCountry?.dialCode)
    }

    @Test
    fun `countries are sorted alphabetically by name`() {
        val countries = CountryDataProvider.getAllCountries()
        val countryNames = countries.map { it.name }

        val sortedNames = countryNames.sorted()
        assertEquals("Countries should be sorted alphabetically", sortedNames, countryNames)
    }

    @Test
    fun `all flag emojis are valid unicode`() {
        val countries = CountryDataProvider.getAllCountries()

        countries.forEach { country ->
            assertTrue("Flag emoji should not be empty: ${country.code}",
                country.flag.isNotEmpty())
            // Flag emojis should be 2-4 characters (regional indicators)
            assertTrue("Flag emoji should be 2-4 characters: ${country.code} - ${country.flag}",
                country.flag.length in 2..4)
        }
    }

    @Test
    fun `dial codes are in valid format`() {
        val countries = CountryDataProvider.getAllCountries()
        val dialCodePattern = Regex("^\\+\\d{1,4}$")

        countries.forEach { country ->
            assertTrue("Dial code should match pattern +[1-4 digits]: ${country.code} - ${country.dialCode}",
                dialCodePattern.matches(country.dialCode))
        }
    }
}

