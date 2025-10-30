package cat.iundarigun.boaleitura.infrastructure.database.configuration

import cat.iundarigun.boaleitura.domain.security.loggedUser
import jakarta.persistence.EntityManager
import org.hibernate.Session
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.TransactionManager

@Configuration
class TransactionConfiguration {
    @Bean
    fun transactionManager(
        transactionManagerCustomizer: ObjectProvider<TransactionManagerCustomizers>
    ): TransactionManager {
        val transactionManager: TransactionManager = object : JpaTransactionManager() {
            override fun createEntityManagerForTransaction(): EntityManager {
                return super.createEntityManagerForTransaction().also {
                    it.unwrap(Session::class.java)
                        .enableFilter("userIdFilter")
                        .setParameter("userId", loggedUser?.userId ?: 0L)
                }
            }
        }
        transactionManagerCustomizer.ifAvailable {
            it.customize(transactionManager)
        }
        return transactionManager
    }
}