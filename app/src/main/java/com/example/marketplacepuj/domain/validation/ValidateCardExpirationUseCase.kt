package com.example.marketplacepuj.domain.validation

import com.example.marketplacepuj.domain.validation.model.ValidationResult
import com.example.marketplacepuj.util.ErrorType

class ValidateCardExpirationUseCase {
    fun execute(expirationTime: Long?): ValidationResult {
        return if (expirationTime == null) {
            ValidationResult(isValid = false, validationError = ErrorType.DATE_UNSPECIFIED)
        } else if (System.currentTimeMillis() > expirationTime) {
            ValidationResult(isValid = false, validationError = ErrorType.CARD_EXPIRED)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}