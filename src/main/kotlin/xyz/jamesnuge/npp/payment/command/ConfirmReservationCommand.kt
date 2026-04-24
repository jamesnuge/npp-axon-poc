package xyz.jamesnuge.npp.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ConfirmReservationCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID
)