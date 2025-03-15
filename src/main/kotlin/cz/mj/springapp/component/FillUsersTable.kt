package cz.mj.springapp.component

import cz.mj.springapp.dto.UserDto
import cz.mj.springapp.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class FillUsersTable(private val userService: UserService) : CommandLineRunner {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun run(vararg args: String?) {
        try {
            userService.create(UserDto(name = "default", username = "default", password = "default"))
        } catch (e: Exception) {
            logger.info { e.message }
        }
    }
}