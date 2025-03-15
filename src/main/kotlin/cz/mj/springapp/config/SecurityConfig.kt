package cz.mj.springapp.config

import cz.mj.springapp.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


data class UserWithId(
    private val username: String,
    private val password: String,
    private val id: Int,
    private val authorities: Collection<GrantedAuthority> = emptyList(),
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    fun getId(): Int = id
}

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .anyRequest().authenticated()
            }.httpBasic(Customizer.withDefaults()).build()
    }

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService = object : UserDetailsService {
        override fun loadUserByUsername(username: String?): UserDetails? {
            if (username == null)
                return null
            try {
                val user = userRepository.getByUsername(username)

                return UserWithId(user.username!!, user.password!!, user.id!!)
            } catch (e: Exception) {
                logger.info(e) {}
                return null
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}