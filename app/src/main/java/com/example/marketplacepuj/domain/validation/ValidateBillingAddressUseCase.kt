package com.example.marketplacepuj.domain.validation

import com.example.marketplacepuj.domain.validation.model.ValidationResult
import com.example.marketplacepuj.util.ErrorType

class ValidateBillingAddressUseCase {
    fun execute(
        addressFirstLine: String,
        addressSecondLine: String
    ): ValidationResult {
        // Check only first line
        return if (addressFirstLine.isBlank()) {
            ValidationResult(true, validationError = ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}