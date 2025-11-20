package me.jaypatelbond.countrypicker

import org.junit.Assert.*
import org.junit.Test

class CountryTest {

    @Test
    fun `country creation with valid data succeeds`() {
        val country = Country(
            name = "United States",
            code = "US",
            flagEmoji = "🇺🇸",
            dialCode = "+1"
        )

        assertEquals("United States", country.name)
        assertEquals("US", country.code)
        assertEquals("🇺🇸", country.flagEmoji)
        assertEquals("+1", country.dialCode)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `country creation with blank name throws exception`() {
        Country(
            name = "",
            code = "US",
            flagEmoji = "🇺🇸",
            dialCode = "+1"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `country creation with blank code throws exception`() {
        Country(
            name = "United States",
            code = "",
            flagEmoji = "🇺🇸",
            dialCode = "+1"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `country creation with invalid code length throws exception`() {
        Country(
            name = "United States",
            code = "USA",
            flagEmoji = "🇺🇸",
            dialCode = "+1"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `country creation with blank flag emoji throws exception`() {
        Country(
            name = "United States",
            code = "US",
            flagEmoji = "",
            dialCode = "+1"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `country creation with blank dial code throws exception`() {
        Country(
            name = "United States",
            code = "US",
            flagEmoji = "🇺🇸",
            dialCode = ""
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `country creation with dial code not starting with plus throws exception`() {
        Country(
            name = "United States",
            code = "US",
            flagEmoji = "🇺🇸",
            dialCode = "1"
        )
    }

    @Test
    fun `getDisplayName returns correct format`() {
        val country = Country(
            name = "United States",
            code = "US",
            flagEmoji = "🇺🇸",
            dialCode = "+1"
        )

        assertEquals("🇺🇸 United States", country.getDisplayName())
    }

    @Test
    fun `getDisplayDialCode returns correct format`() {
        val country = Country(
            name = "United States",
            code = "US",
            flagEmoji = "🇺🇸",
            dialCode = "+1"
        )

        assertEquals("🇺🇸 +1", country.getDisplayDialCode())
    }

    @Test
    fun `two countries with same data are equal`() {
        val country1 = Country("United States", "US", "🇺🇸", "+1")
        val country2 = Country("United States", "US", "🇺🇸", "+1")

        assertEquals(country1, country2)
        assertEquals(country1.hashCode(), country2.hashCode())
    }

    @Test
    fun `two countries with different data are not equal`() {
        val country1 = Country("United States", "US", "🇺🇸", "+1")
        val country2 = Country("Canada", "CA", "🇨🇦", "+1")

        assertNotEquals(country1, country2)
    }
}

