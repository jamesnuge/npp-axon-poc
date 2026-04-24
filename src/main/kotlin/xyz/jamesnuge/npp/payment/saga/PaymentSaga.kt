package xyz.jamesnuge.npp.payment.saga

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired
import xyz.jamesnuge.npp.ledger.LedgerPort
import xyz.jamesnuge.npp.ledger.ReservationRequest
import xyz.jamesnuge.npp.payment.event.CustomerPaymentInitiatedEvent
import xyz.jamesnuge.npp.payment.event.PaymentValidatedEvent
import xyz.jamesnuge.npp.validation.ValidationPort
import java.util.UUID

@Saga
class PaymentSaga {

    private lateinit var reservationId: UUID

    @Autowired
    @Transient
    private lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "paymentId")
    fun on(event: CustomerPaymentInitiatedEvent, validationPort: ValidationPort) {
        validationPort.validatePayment(event.paymentId)
    }

    @SagaEventHandler(associationProperty = "paymentId")
    fun on(event: PaymentValidatedEvent, ledgerPort: LedgerPort) {
        with(event) {
            reservationId = UUID.randomUUID()
            ledgerPort.reserveFunds(
                ReservationRequest(reservationId, account, amount, paymentId)
            )
        }
    }
}