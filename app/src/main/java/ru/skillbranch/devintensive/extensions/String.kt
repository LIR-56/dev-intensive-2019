package ru.skillbranch.devintensive.extensions

fun String.truncate(amountOfLetters: Int = 16) = if (this.trim().length <= amountOfLetters) this.trim()
else {
    (this.substring(0, amountOfLetters).trim() + "...")
}


fun String.stripHtml(): String {
    val result = StringBuilder()
    var i = 0
    while (i < this.length) {
        when (this[i]) {
            '<' -> {
                while (this[i] != '>' && i < this.length) i++
                i++
            }
            ' ' -> {
                while (this[i] == ' ') i++
                result.append(" ")
            }
            '&' -> {
                while(this[i]!=' ') i++
                i++
            }
            else -> {
                result.append(this[i])
                i++
            }
        }
    }
    return result.toString()

}