package cz.mj.springapp.repository

import cz.mj.springapp.jooq.tables.records.UsersRecord
import cz.mj.springapp.jooq.tables.references.USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val dbContext: DSLContext) {
    fun getById(id: Int) = dbContext.selectFrom(USERS).where(USERS.ID.eq(id)).fetchSingle()
    fun getAll() = dbContext.selectFrom(USERS).fetch()
    fun create(user: UsersRecord) = dbContext.insertInto(USERS).set(user.copy()).execute()
    fun update(user: UsersRecord) = dbContext.update(USERS).set(user.copy()).where(USERS.ID.eq(user.id)).execute()
    fun delete(id: Int) = dbContext.delete(USERS).where(USERS.ID.eq(id)).execute()
}