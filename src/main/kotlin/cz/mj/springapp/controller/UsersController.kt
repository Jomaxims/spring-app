package cz.mj.springapp.controller

import cz.mj.springapp.dto.UserDto
import cz.mj.springapp.service.UserService
import jakarta.validation.Valid
import org.jooq.exception.NoDataFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UserService) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserDto {
        try {
            return userService.getById(id)
        } catch (e: NoDataFoundException) {
            throw UserNotFoundException("Uživatel nenalezen", e)
        }
    }

    @GetMapping
    fun getUsers(): List<UserDto> {
        return userService.getAll()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postUser(@RequestBody @Valid user: UserDto) {
        val res = userService.create(user)
        if (res != 1)
            throw UserNotCreatedException("Nepodařilo se vytvořit uživatele")
    }

    @PutMapping
    fun putUser(@RequestBody @Valid user: UserDto) {
        val res = userService.update(user)
        if (res != 1)
            throw UserNotFoundException("Uživatel nenalezen")
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int) {
        val result = userService.delete(id)
        if (result != 1)
            throw UserNotFoundException("Uživatel nenalezen")
    }
}