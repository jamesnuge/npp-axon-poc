package xyz.jamesnuge.npp.audit.payment

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentAuditRepository: CrudRepository<PaymentAudit, UUID> {
}