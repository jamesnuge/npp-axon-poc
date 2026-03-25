package xyz.jamesnuge.npp.payment

import xyz.jamesnuge.npp.payment.event.CustomerPaymentInitiatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import xyz.jamesnuge.npp.payment.command.CreateCustomerPayment
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
}

enum class PaymentType { Debit, Credit }
enum class Origin { Internal, External }
enum class State { Initiated }