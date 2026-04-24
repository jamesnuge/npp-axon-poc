package xyz.jamesnuge.npp.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class ConfirmValidationCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID
)
