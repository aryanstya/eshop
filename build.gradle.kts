plugins {
	java
	jacoco
	id("org.sonarqube") version "4.4.1.3373"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}


configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.seleniumhq.selenium:selenium-java:4.14.1")
	testImplementation("io.github.bonigarcia:selenium-jupiter:5.0.1")
	testImplementation("io.github.bonigarcia:webdrivermanager:5.6.3")
	testImplementation("org.junit.jupiter:junit-jupiter")

}


tasks.register<Test>( "unitTest") {
	description = "Runs unit tests."
	group = "verification"

	filter {
		excludeTestsMatching("*FunctionalTest")

	}
}
tasks.register<Test>( "functionalTest") {
	description = "Runs functional tests."
	group = "verification"

	filter {
		includeTestsMatching("*FunctionalTest")

	}

}

tasks.test {
	filter {
		excludeTestsMatching("*FunctionalTest")
	}

	finalizedBy(tasks.jacocoTestReport)

}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
sonarqube {
	properties {
		property("sonar.projectKey", "aryanstya_eshop2")
		property("sonar.organization", "aryanstya")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}