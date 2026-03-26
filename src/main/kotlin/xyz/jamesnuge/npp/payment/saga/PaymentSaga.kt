package xyz.jamesnuge.npp.payment.saga

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired
import xyz.jamesnuge.npp.payment.command.ValidatePaymentCommand
import xyz.jamesnuge.npp.payment.event.CustomerPaymentInitiatedEvent

@Saga
class PaymentSaga {

    @Autowired
    @Transient
    private lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "paymentId")
    fun on(event: CustomerPaymentInitiatedEvent) {
        commandGateway.sendAndWait<Any>(ValidatePaymentCommand(event.paymentId));
    }
}