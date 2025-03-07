package cz.mj.springapp.service

import cz.mj.springapp.dto.toDto
import cz.mj.springapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getById(id: Int) = userRepository.getById(id).toDto()
}