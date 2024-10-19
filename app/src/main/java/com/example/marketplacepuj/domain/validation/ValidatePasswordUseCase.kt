package com.example.marketplacepuj.domain.validation

import com.example.marketplacepuj.domain.validation.model.ValidationResult
import com.example.marketplacepuj.util.ErrorType

class ValidatePasswordUseCase {
    fun execute(password: String): ValidationResult {
        return if (isPasswordValid(password)) {
            ValidationResult(true, null)
        } else if (password.isBlank()) {
            ValidationResult(false, ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(false, ErrorType.INVALID_PASSWORD_FIELD)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.firstOrNull { it.isLetter() } == null) return false

        return true
    }
}