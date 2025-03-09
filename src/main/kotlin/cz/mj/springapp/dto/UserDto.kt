package cz.mj.springapp.dto

import cz.mj.springapp.jooq.tables.records.UsersRecord
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserDto(
    val id: Int?,

    @get:NotBlank(message = "Jméno uživatele nesmí být prázdné")
    @get:Size(max = 32, message = "Jméno uživatele nesmí přesahovat 32 znaků")
    val name: String?,
)

fun UsersRecord.toDto() = UserDto(this.id, this.name)
fun UserDto.toRecord() = UsersRecord(this.id, this.name)