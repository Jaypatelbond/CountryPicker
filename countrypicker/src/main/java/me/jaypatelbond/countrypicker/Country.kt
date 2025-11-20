package me.jaypatelbond.countrypicker

/**
 * Represents a country with its details including name, code, flag emoji, and dial code.
 *
 * @property name The full name of the country (e.g., "United States")
 * @property code The ISO 3166-1 alpha-2 country code (e.g., "US")
 * @property flagEmoji The emoji representation of the country's flag (e.g., "🇺🇸")
 * @property dialCode The international dialing code (e.g., "+1")
 *
 * @throws IllegalArgumentException if any parameter is blank or invalid
 */
data class Country(
    val name: String,
    val code: String,
    val flagEmoji: String,
    val dialCode: String
) {
    init {
        require(name.isNotBlank()) { "Country name cannot be blank" }
        require(code.isNotBlank()) { "Country code cannot be blank" }
        require(code.length == 2) { "Country code must be 2 characters" }
        require(flagEmoji.isNotBlank()) { "Flag emoji cannot be blank" }
        require(dialCode.isNotBlank()) { "Dial code cannot be blank" }
        require(dialCode.startsWith("+")) { "Dial code must start with +" }
    }

    /**
     * Returns a formatted display string combining the flag emoji and country name.
     */
    fun getDisplayName(): String = "$flagEmoji $name"

    /**
     * Returns a formatted display string combining the flag emoji and dial code.
     */
    fun getDisplayDialCode(): String = "$flagEmoji $dialCode"
}

