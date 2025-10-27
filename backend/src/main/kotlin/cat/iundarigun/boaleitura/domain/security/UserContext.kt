package cat.iundarigun.boaleitura.domain.security

object UserContext {
    private val applicationUserThread = ThreadLocal<ApplicationUser>()

    fun setApplicationUser(applicationUser: ApplicationUser) {
        applicationUserThread.set(applicationUser)
    }

    fun clear() {
        applicationUserThread.remove()
    }

    fun getApplicationUser(): ApplicationUser? {
        return applicationUserThread.get() ?: null
    }

    fun getApplicationUserId(): Long? {
        return applicationUserThread.get()?.id
    }
}
