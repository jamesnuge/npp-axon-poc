package xyz.jamesnuge.npp.payment.saga

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import xyz.jamesnuge.npp.payment.command.ValidatePaymentCommand
import xyz.jamesnuge.npp.payment.event.CustomerPaymentInitiatedEvent

@Saga
class PaymentSaga(val commandGateway: CommandGateway) {

    @StartSaga
    @SagaEventHandler(associationProperty = "paymentId")
    fun on(event: CustomerPaymentInitiatedEvent) {
        commandGateway.sendAndWait<Any>(ValidatePaymentCommand(event.paymentId));
    }
}