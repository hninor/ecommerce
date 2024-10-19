package com.example.marketplacepuj.domain.validation

import com.example.marketplacepuj.domain.validation.model.ValidationResult
import com.example.marketplacepuj.util.ErrorType

class ValidateCardHolderUseCase {
    fun execute(cardHolder: String): ValidationResult {
        return if (cardHolder.isNotBlank()) {
            ValidationResult(isValid = true)
        } else {
            ValidationResult(isValid = false, validationError = ErrorType.FIELD_IS_EMPTY)
        }
    }
}