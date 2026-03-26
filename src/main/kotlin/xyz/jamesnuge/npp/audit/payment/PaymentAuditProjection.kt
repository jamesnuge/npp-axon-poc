package xyz.jamesnuge.npp.audit.payment

import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import xyz.jamesnuge.npp.payment.Origin
import xyz.jamesnuge.npp.payment.PaymentType
import xyz.jamesnuge.npp.payment.State
import xyz.jamesnuge.npp.payment.event.CustomerPaymentInitiatedEvent

@Component
class PaymentAuditProjection(val paymentAuditRepository: PaymentAuditRepository) {

    @EventHandler
    fun on(event: CustomerPaymentInitiatedEvent) {
        paymentAuditRepository.save(event.toAuditModel());
    }

    private fun CustomerPaymentInitiatedEvent.toAuditModel(): PaymentAudit = PaymentAudit(
        paymentId = paymentId,
        amount = amount.toBigDecimal(),
        payerId = payerId,
        payeeId = payeeId,
        paymentType = PaymentType.Debit,
        origin = Origin.Internal,
        state = State.Initiated
    )

}