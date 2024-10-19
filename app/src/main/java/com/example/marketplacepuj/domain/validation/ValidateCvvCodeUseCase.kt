package com.example.marketplacepuj.domain.validation

import com.example.marketplacepuj.domain.validation.model.ValidationResult
import com.example.marketplacepuj.util.ErrorType

class ValidateCvvCodeUseCase {
    fun execute(cvv: String): ValidationResult {
        return if (cvv.isBlank()) {
            ValidationResult(isValid = false, validationError = ErrorType.FIELD_IS_EMPTY)
        } else {
            // Regular expression for 3 or 4 digits
            val cvvPattern = "\\d{3,4}".toRegex()

            if (cvvPattern.matches(cvv)) {
                ValidationResult(isValid = true, validationError = null)
            } else {
                ValidationResult(isValid = false, validationError = ErrorType.INVALID_CVV)
            }
        }
    }
}