package xyz.jamesnuge.npp.ledger

import java.math.BigDecimal
import java.util.*

interface LedgerPort {
    fun reserveFunds(request: ReservationRequest)
    fun confirmReservation(paymentId: UUID)
}

data class ReservationRequest(
    val reservationId: UUID,
    val accountId: String,
    val amount: BigDecimal,
    val paymentId: UUID // paymentId
)

interface LedgerResponsePort {
    fun processSuccessfulReservation(reservationId: UUID)
    fun processFailedReservation(reservationId: UUID)
}