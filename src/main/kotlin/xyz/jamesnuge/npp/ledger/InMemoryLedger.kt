package xyz.jamesnuge.npp.ledger

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Component
import xyz.jamesnuge.npp.payment.command.ConfirmReservationCommand
import java.util.*

@Component
class InMemoryLedger(private val commandGateway: CommandGateway): LedgerPort, LedgerResponsePort {

    override fun reserveFunds(request: ReservationRequest) {
        processSuccessfulReservation(request.reservationId);
    }

    override fun confirmReservation(paymentId: UUID) {
        processSuccessfulReservation(paymentId);
    }

    override fun processSuccessfulReservation(reservationId: UUID) {
        commandGateway.sendAndWait<Unit>(ConfirmReservationCommand(reservationId))
    }

    override fun processFailedReservation(reservationId: UUID) {
        commandGateway.sendAndWait<Unit>("");
    }
}