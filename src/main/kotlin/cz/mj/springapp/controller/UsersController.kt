package cz.mj.springapp.controller

import cz.mj.springapp.dto.UserDto
import cz.mj.springapp.service.UserService
import org.jooq.exception.NoDataFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class UserNotFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UserService) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<UserDto> {
        try {
            val user = userService.getById(id)
            return ResponseEntity(user, HttpStatus.OK)
        } catch (e: NoDataFoundException) {
            throw UserNotFoundException("Uživatel nenalezen", e)
        }
    }
}