package xyz.jamesnuge.npp.payment.event

import java.util.*

data class CustomerPaymentInitiatedEvent(
    val paymentId: UUID,
    val payerId: String,
    val payeeId: String,
    val amount: Long
)