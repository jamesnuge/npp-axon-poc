package xyz.jamesnuge.npp.audit.payment

import jakarta.persistence.Entity
import jakarta.persistence.Id
import xyz.jamesnuge.npp.payment.Origin
import xyz.jamesnuge.npp.payment.PaymentType
import xyz.jamesnuge.npp.payment.State
import java.math.BigDecimal
import java.util.UUID

@Entity
data class PaymentAudit(
    @Id
    val paymentId: UUID = UUID.randomUUID(),
    val amount: BigDecimal,
    val payerId: String,
    val payeeId: String,
    val paymentType: PaymentType,
    val origin: Origin,
    val state: State,
)