package cz.mj.springapp.config

import org.jooq.conf.RenderQuotedNames
import org.jooq.impl.DefaultConfiguration
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JooqConfig {
    @Bean
    fun configurationCustomizer() = DefaultConfigurationCustomizer { c: DefaultConfiguration ->
        c.settings()
            .withRenderQuotedNames(
                RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED
            )
    }
}