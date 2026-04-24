package xyz.jamesnuge.npp.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ReserveFundsCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID
)