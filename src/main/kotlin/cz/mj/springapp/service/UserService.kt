package cz.mj.springapp.service

import cz.mj.springapp.dto.UserDto
import cz.mj.springapp.dto.toDto
import cz.mj.springapp.dto.toRecord
import cz.mj.springapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getById(id: Int) = userRepository.getById(id).toDto()
    fun getAll() = userRepository.getAll().map { it.toDto() }
    fun create(user: UserDto) = userRepository.create(user.toRecord())
    fun update(user: UserDto) = userRepository.update(user.toRecord())
    fun delete(id: Int) = userRepository.delete(id)
}