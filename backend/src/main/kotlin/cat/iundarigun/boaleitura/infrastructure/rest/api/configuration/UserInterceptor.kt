package cat.iundarigun.boaleitura.infrastructure.rest.api.configuration

import cat.iundarigun.boaleitura.domain.security.ApplicationUser
import cat.iundarigun.boaleitura.domain.security.UserContext
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.ui.ModelMap
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.WebRequestInterceptor
import org.springframework.web.servlet.handler.DispatcherServletWebRequest

@Component
class UserInterceptor : WebRequestInterceptor {
    override fun preHandle(request: WebRequest) {
        val webRequest = request as DispatcherServletWebRequest
        if (webRequest.httpMethod != HttpMethod.OPTIONS) {
            val userId = webRequest.getHeader("X-User-Id")?.toLongOrNull()
            userId?.let {
                UserContext.setApplicationUser(ApplicationUser(it))
            } ?: throw IllegalArgumentException("User not provided")
        }
    }

    override fun postHandle(request: WebRequest, model: ModelMap?) {
        //
    }

    override fun afterCompletion(request: WebRequest, ex: Exception?) {
        UserContext.clear()
    }
}