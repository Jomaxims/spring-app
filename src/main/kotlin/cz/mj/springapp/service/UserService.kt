package cz.mj.springapp.service

import cz.mj.springapp.dto.UserDto
import cz.mj.springapp.dto.UserUpdateDto
import cz.mj.springapp.dto.toDto
import cz.mj.springapp.dto.toRecord
import cz.mj.springapp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {
    fun getById(id: Int) = userRepository.getById(id).toDto()
    fun getAll() = userRepository.getAll().map { it.toDto() }
    fun create(user: UserDto) =
        userRepository.create(user.toRecord().apply { password = passwordEncoder.encode(password) })

    fun update(id: Int, user: UserUpdateDto) = userRepository.update(user.toRecord().apply {
        this.id = id
        if (this.password != null)
            this.password = passwordEncoder.encode(password)
    })

    fun delete(id: Int) = userRepository.delete(id)
}