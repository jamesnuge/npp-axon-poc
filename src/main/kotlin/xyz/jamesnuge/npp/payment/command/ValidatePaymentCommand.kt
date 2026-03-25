package xyz.jamesnuge.npp.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ValidatePaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID
)