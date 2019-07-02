package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        return when {
            fullName == null -> null to null
            fullName == "" -> null to null
            fullName.matches(Regex(pattern = "[ ]*")) -> null to null
            fullName.contains(" ") -> fullName.substring(
                0,
                fullName.indexOf(" ")
            ) to fullName.substring(fullName.indexOf(" ") + 1)
            else -> fullName to null
        }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val fnNull = firstName == null
        val lnNull = lastName == null
        val fnEmpty = firstName?.matches(Regex(pattern = "[ \\t\\n\\x0B\\f\\r]*"))
        val lnEmpty = lastName?.matches(Regex(pattern = "[ \\t\\n\\x0B\\f\\r]*"))

        val fnNoInit = (fnNull || fnEmpty!!)
        val lnNoInit = (lnNull || lnEmpty!!)

        return when {
            fnNoInit && lnNoInit -> null
            fnNoInit -> lastName?.toUpperCase()?.first().toString()
            lnNoInit -> firstName?.toUpperCase()?.first().toString()
            else -> firstName?.toUpperCase()?.first().toString() + lastName?.toUpperCase()?.first().toString()
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val result = StringBuilder()
        for (i in payload.toCharArray()) {
            result.append(
                when (i) {
                    ' ' -> divider
                    else -> transliterate(i)
                }
            )
        }
        return result.toString()
    }

    private fun transliterate(letter: Char): String {
        val isUpper = letter.isUpperCase()
        val letterSmalled = letter.toLowerCase()
        var result = when (letterSmalled) {
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ё' -> "e"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "i"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "h"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "sh'"
            'ъ' -> ""
            'ы' -> "i"
            'ь' -> ""
            'э' -> "e"
            'ю' -> "yu"
            'я' -> "ya"
            else -> letter.toString()
        }
        if (isUpper) result = result.capitalize()
        return result
    }

}