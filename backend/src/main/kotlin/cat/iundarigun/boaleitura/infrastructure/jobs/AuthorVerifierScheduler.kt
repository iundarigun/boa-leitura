package cat.iundarigun.boaleitura.infrastructure.jobs

import cat.iundarigun.boaleitura.application.port.input.author.DeleteOrphanAuthorsUseCase
import org.jobrunr.jobs.annotations.Job
import org.jobrunr.jobs.annotations.Recurring
import org.springframework.stereotype.Component

@Component
class AuthorVerifierScheduler(
    private val deleteOrphanAuthorsUseCase: DeleteOrphanAuthorsUseCase
) {

    @Recurring(id = "execute-author-verifier-scheduler", cron = "0 0 * * *")
    @Job(name = "verify Authors who can be deleted")
    fun verifyAuthorsJob() {
        deleteOrphanAuthorsUseCase.execute()
    }
}