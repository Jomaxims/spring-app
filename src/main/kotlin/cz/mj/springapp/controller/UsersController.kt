package cz.mj.springapp.controller

import cz.mj.springapp.dto.UserDto
import cz.mj.springapp.dto.UserUpdateDto
import cz.mj.springapp.service.UserService
import jakarta.validation.Valid
import org.jooq.exception.NoDataFoundException
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UserService) {
    @PreAuthorize("#id == authentication.principal.getId()")
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

    @PreAuthorize("#id == authentication.principal.getId()")
    @PutMapping("/{id}")
    fun putUser(@PathVariable id: Int, @RequestBody @Valid user: UserUpdateDto) {
        try {
            val res = userService.update(id, user)
            if (res != 1)
                throw UserNotFoundException("Uživatel nenalezen")
        } catch (e: DuplicateKeyException) {
            throw UserDuplicateNameException("Uživatel se stejným uživatelským jménem již existuje", e)
        }
    }

    @PreAuthorize("#id == authentication.principal.getId()")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int) {
        val result = userService.delete(id)
        if (result != 1)
            throw UserNotFoundException("Uživatel nenalezen")
    }
}