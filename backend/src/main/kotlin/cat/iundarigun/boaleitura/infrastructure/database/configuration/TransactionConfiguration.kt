package cat.iundarigun.boaleitura.infrastructure.database.configuration

import cat.iundarigun.boaleitura.domain.security.loggedUser
import jakarta.persistence.EntityManager
import org.hibernate.Session
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.TransactionManager

@Configuration
class TransactionConfiguration(private val env: Environment) {
    @Bean
    fun transactionManager(
        transactionManagerCustomizer: ObjectProvider<TransactionManagerCustomizers>
    ): TransactionManager {
        val transactionManager: TransactionManager = object : JpaTransactionManager() {
            override fun createEntityManagerForTransaction(): EntityManager {
                return super.createEntityManagerForTransaction().also {
                    if (!env.activeProfiles.contains("test") || loggedUser?.userId != null) {
                        it.unwrap(Session::class.java)
                            .enableFilter("userIdFilter")
                            .setParameter("userId", loggedUser?.userId ?: 0L)
                    }
                }
            }
        }
        transactionManagerCustomizer.ifAvailable {
            it.customize(transactionManager)
        }
        return transactionManager
    }
}