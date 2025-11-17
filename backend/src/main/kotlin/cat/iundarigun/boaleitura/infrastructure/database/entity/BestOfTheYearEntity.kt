package cat.iundarigun.boaleitura.infrastructure.database.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Version
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "BestOfTheYear")
data class BestOfTheYearEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    var year: Int,

    @ManyToOne
    @JoinColumn(name = "january")
    var january: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "february")
    var february: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "march")
    var march: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "april")
    var april: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "may")
    var may: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "june")
    var june: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "july")
    var july: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "august")
    var august: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "september")
    var september: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "october")
    var october: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "november")
    var november: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "december")
    var december: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "quarter_one")
    var quarterOne: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "quarter_two")
    var quarterTwo: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "quarter_three")
    var quarterThree: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "quarter_four")
    var quarterFour: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "first_half")
    var firstHalf: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "second_half")
    var secondHalf: ReadingEntity? = null,

    @ManyToOne
    @JoinColumn(name = "best_of_the_year")
    var bestOfTheYear: ReadingEntity? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Version
    var version: Int = 0
) : UserIdBaseEntity()