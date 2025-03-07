package cz.mj.springapp.dto

import cz.mj.springapp.jooq.tables.records.UsersRecord

data class UserDto(val id: Int?, val name: String?)

fun UsersRecord.toDto() = UserDto(this.id, this.name)