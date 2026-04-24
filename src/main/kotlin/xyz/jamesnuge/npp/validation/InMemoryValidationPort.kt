package xyz.jamesnuge.npp.validation

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import xyz.jamesnuge.npp.payment.command.ConfirmValidationCommand
import java.util.UUID

@Service
class InMemoryValidationImpl(val commandGateway: CommandGateway) : ValidationPort, ValidationResponsePort {
    override fun validatePayment(paymentId: UUID) {
        confirmValidation(paymentId);
    }

    override fun confirmValidation(paymentId: UUID) {
        commandGateway.send<Unit>(ConfirmValidationCommand(paymentId))
    }
}
