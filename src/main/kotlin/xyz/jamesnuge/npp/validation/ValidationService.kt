package xyz.jamesnuge.npp.validation

import org.springframework.stereotype.Service
import xyz.jamesnuge.npp.payment.PaymentAggregate

interface ValidationService {
    fun validatePayment(payment: PaymentAggregate): ValidationResult
}

enum class ValidationResult {
    Valid, OutOfFunds, Fraudulent
}

@Service
class ValidationServiceImpl : ValidationService {
    override fun validatePayment(payment: PaymentAggregate): ValidationResult {
        return ValidationResult.Valid
    }
}