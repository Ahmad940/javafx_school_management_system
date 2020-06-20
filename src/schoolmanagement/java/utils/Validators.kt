package schoolmanagement.java.utils

import java.util.regex.Pattern

object Validators {
    fun isValidEmail(value: String): Boolean {
        val pattern = Pattern.compile("\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}")
        val matcher = pattern.matcher(value)
        return matcher.matches()
    }

    fun isNumber(value: String): Boolean {
        var isNumber = true
        val letters = value.toCharArray()
        // check if empty
        if (letters.size < 1) {
            return false
        }
        for (letter in letters) {
            if (!Character.isDigit(letter)) {
                isNumber = false
            }
        }
        return isNumber
    }
}

