package schoolmanagement.java.utils

object Validators {
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

