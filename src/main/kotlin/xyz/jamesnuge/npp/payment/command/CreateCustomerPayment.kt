package xyz.jamesnuge.npp.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateCustomerPayment(
    @TargetAggregateIdentifier
    val paymentId: UUID,
    val customerAccountId: String,
    val payeeId: String,
    val amount: Long
)