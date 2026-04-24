package xyz.jamesnuge.npp.validation

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import xyz.jamesnuge.npp.payment.PaymentAggregate
import xyz.jamesnuge.npp.payment.command.ConfirmValidationCommand
import xyz.jamesnuge.npp.payment.event.PaymentValidatedEvent
import java.util.*

interface ValidationPort {
    fun validatePayment(paymentId: UUID)
}

interface ValidationResponsePort {
    fun confirmValidation(paymentId: UUID)
}

enum class ValidationResult {
    Valid, OutOfFunds, Fraudulent
}
