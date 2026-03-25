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
    fun createPayment(@RequestParam amount: Long): String {
        val paymentId = UUID.randomUUID()
        commandGateway.sendAndWait<Void>(CreateCustomerPayment(paymentId, amount))
        return "Payment created with ID: $paymentId"
    }
}