package xyz.jamesnuge.npp.api

import xyz.jamesnuge.npp.payment.command.CreateCustomerPayment
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentController(
    private val commandGateway: CommandGateway
) {

    @PostMapping
    fun createPayment(@RequestBody request: PaymentRequest): String {
        val paymentId = UUID.randomUUID()
        commandGateway.sendAndWait<Void>(request.toCommand(paymentId));
        return "Payment created with ID: $paymentId"
    }

    private fun PaymentRequest.toCommand(paymentId: UUID): CreateCustomerPayment = CreateCustomerPayment(
        paymentId = paymentId,
        customerAccountId = payerId,
        payeeId = payeeId,
        amount = amount
    )
}

data class PaymentRequest(val payeeId: String, val amount: Long, val payerId: String)