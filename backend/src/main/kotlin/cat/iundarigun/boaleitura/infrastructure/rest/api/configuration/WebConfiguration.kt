package cat.iundarigun.boaleitura.infrastructure.rest.api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration(private val userInterceptor: UserInterceptor) : WebMvcConfigurer {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
            }
        }
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addWebRequestInterceptor(userInterceptor)
            .excludePathPatterns("/swagger-ui/**", "/**/api-docs/**", "/auth/**")
    }
}