package com.example.marketplacepuj.domain.validation.model

import com.example.marketplacepuj.util.ErrorType


data class ValidationResult(
    val isValid: Boolean,
    val validationError: ErrorType? = null
)
