package xyz.jamesnuge.npp.payment.event

import java.math.BigDecimal
import java.util.*

data class PaymentValidatedEvent (
    val paymentId: UUID,
    val amount: BigDecimal,
    val account: String,
)