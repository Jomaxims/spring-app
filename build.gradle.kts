plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jooq.jooq-codegen-gradle") version "3.19.1"
}

group = "cz.mj"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    jooqCodegen("org.jooq:jooq-meta-extensions-liquibase:3.19.1")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

jooq {
    configuration {
        generator {
            database {
                name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"

                properties {
                    property {
                        key = "dialect"
                        value = "Postgres"
                    }
                    property {
                        key = "rootPath"
                        value = "$rootDir/src/main/resources/db/changelog"
                    }
                    property {
                        key = "scripts"
                        value = "db.changelog-master.yaml"
                    }
                }
            }
            name = "org.jooq.codegen.KotlinGenerator"
            target {
                packageName = "cz.mj.springapp.jooq"
                directory = "build/generated/jooq"
            }
            generate {
                isKotlinNotNullPojoAttributes = true
            }
        }
    }
}

tasks["compileKotlin"].dependsOn("jooqCodegen")

tasks.withType<Test> {
    useJUnitPlatform()
}
