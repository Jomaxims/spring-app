package cz.mj.springapp.repository

import cz.mj.springapp.jooq.tables.records.UsersRecord
import cz.mj.springapp.jooq.tables.references.USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val dbContext: DSLContext) {
    fun getById(id: Int) = UsersRecord(id = id).attach().apply { refresh() }
    fun getByUsername(username: String) = dbContext.selectFrom(USERS).where(USERS.USERNAME.eq(username)).fetchSingle()
    fun getAll() = dbContext.fetch(USERS)
    fun create(user: UsersRecord) = user.copy().attach().store()
    fun update(user: UsersRecord) = user.attach().apply {
        changed(USERS.ID, false)
        fields().forEach { if (user[it] == null) changed(it, false) }
    }.store()

    fun delete(id: Int) = UsersRecord(id = id).attach().delete()

    private fun UsersRecord.attach() = this.apply { attach(dbContext.configuration()) }
}