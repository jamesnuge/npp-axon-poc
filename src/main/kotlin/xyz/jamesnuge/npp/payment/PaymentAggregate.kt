package xyz.jamesnuge.npp.payment

import xyz.jamesnuge.npp.payment.event.CustomerPaymentInitiatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import xyz.jamesnuge.npp.payment.command.ConfirmReservationCommand
import xyz.jamesnuge.npp.payment.command.ConfirmValidationCommand
import xyz.jamesnuge.npp.payment.command.CreateCustomerPayment
import xyz.jamesnuge.npp.payment.event.PaymentValidatedEvent
import java.math.BigDecimal
import java.util.*

@Aggregate
class PaymentAggregate() {

    @AggregateIdentifier
    private lateinit var paymentId: UUID
    private lateinit var amount: BigDecimal

    private lateinit var payerId: String
    private lateinit var payeeId: String

    private lateinit var type: PaymentType
    private lateinit var origin: Origin
    private lateinit var state: State


    @CommandHandler
    constructor(command: CreateCustomerPayment) : this() {
        with(command) {
            apply(
                CustomerPaymentInitiatedEvent(
                    paymentId = paymentId,
                    payerId = customerAccountId,
                    payeeId = payeeId,
                    amount = amount
                )
            )
        }
    }

    @CommandHandler
    fun handle(command: ConfirmValidationCommand) {
        with(command) {
            if (type == PaymentType.Debit) {
                apply(
                    PaymentValidatedEvent(paymentId, amount, payeeId)
                )
            } else {
                // TODO: publish funds reserved event
            }
        }
    }

    @CommandHandler
    fun handle(command: ConfirmReservationCommand) {
        
    }

    @EventSourcingHandler
    fun on(event: CustomerPaymentInitiatedEvent) {
        this.paymentId = event.paymentId
        this.amount = event.amount.toBigDecimal()
        this.type = PaymentType.Debit
        this.origin = Origin.Internal
        this.state = State.Initiated
        this.payerId = event.payerId
        this.payeeId = event.payeeId
    }

    @EventSourcingHandler
    fun on(event: PaymentValidatedEvent) {
        this.state = State.Validated
    }
}

enum class PaymentType { Debit, Credit }
enum class Origin { Internal, External }
enum class State { Initiated, Validated, Reserved }