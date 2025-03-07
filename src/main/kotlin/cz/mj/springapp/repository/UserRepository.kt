package cz.mj.springapp.repository

import cz.mj.springapp.jooq.tables.references.USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val dbContext: DSLContext) {
    fun getById(id: Int) = dbContext.selectFrom(USERS).where(USERS.ID.eq(id)).fetchSingle()
}