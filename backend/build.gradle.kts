plugins {
	kotlin("jvm") version "2.0.21"
	kotlin("plugin.spring") version "2.0.21"
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.0.21"
	id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

group = "cat.iundarigun.boaleitura"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val datafakerVersion = "2.3.1"
val openCsvVersion = "5.9"
val springdocOpenapiStarterWebmvcUIVersion = "2.6.0"
val detektVersion = "1.23.8"
val jobrunrVersion = "8.1.0"
val archUnitVersion = "1.4.1"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.retry:spring-retry")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")

	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")

	implementation("org.jobrunr:jobrunr-spring-boot-3-starter:$jobrunrVersion")

	implementation("com.opencsv:opencsv:$openCsvVersion")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapiStarterWebmvcUIVersion")

	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("net.datafaker:datafaker:$datafakerVersion")

	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")

	testImplementation("io.rest-assured:rest-assured")
	testImplementation("com.tngtech.archunit:archunit:$archUnitVersion")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
	}
}

detekt {
	source = files("src/main/kotlin", "src/test/kotlin")
	config = files("custom-detekt.yaml")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
