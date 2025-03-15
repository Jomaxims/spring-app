package cz.mj.springapp.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import cz.mj.springapp.jooq.tables.records.UsersRecord
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@JsonInclude(Include.NON_NULL)
data class UserDto(
    val id: Int? = null,

    @get:NotBlank(message = "Jméno uživatele nesmí být prázdné")
    @get:Size(max = 32, message = "Jméno uživatele nesmí přesahovat 32 znaků")
    val name: String? = null,

    @get:NotBlank(message = "Uživatelské jméno nesmí být prázdné")
    @get:Size(max = 32, message = "Uživatelské jméno nesmí přesahovat 32 znaků")
    val username: String? = null,

    @get:NotBlank(message = "Heslo nesmí být prázdné")
    val password: String? = null,
)

fun UsersRecord.toDto() = UserDto(id = this.id, name = this.name, username = this.username, password = null)
fun UserDto.toRecord() = UsersRecord(id = this.id, name = this.name, username = this.username, password = this.password)

data class UserUpdateDto(
    @get:Pattern(regexp = "^(?!\\s*$).+", message = "Jméno uživatele nesmí být prázdné")
    @get:Size(max = 32, message = "Jméno uživatele nesmí přesahovat 32 znaků")
    val name: String? = null,

    @get:Pattern(regexp = "^(?!\\s*$).+", message = "Uživatelské jméno nesmí být prázdné")
    @get:Size(max = 32, message = "Uživatelské jméno nesmí přesahovat 32 znaků")
    val username: String? = null,

    @get:Pattern(regexp = "^(?!\\s*$).+", message = "Heslo nesmí být prázdné")
    val password: String? = null,
)

fun UserUpdateDto.toRecord() = UsersRecord(name = this.name, username = this.username, password = this.password)