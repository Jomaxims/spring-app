package cz.mj.springapp.component

import cz.mj.springapp.jooq.tables.records.UsersRecord
import cz.mj.springapp.jooq.tables.references.USERS
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jooq.DSLContext
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class FillUsersTable(private val dbContext: DSLContext) : CommandLineRunner {
    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun run(vararg args: String?) {
        if (dbContext.fetchCount(USERS) > 0) {
            logger.info { "Table USERS has data" }

            return
        }

        listOf(
            UsersRecord(name = "franta"),
            UsersRecord(name = "pepa"),
        ).let {
            dbContext.batchInsert(it).execute()
        }
    }
}