package cat.iundarigun.boaleitura.architecture

import cat.iundarigun.boaleitura.BoaLeituraApplication
import cat.iundarigun.boaleitura.infrastructure.security.configuration.JwtProperties
import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.properties.HasName
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.Test

class ArchUnitTest {
    companion object {
        val basePackage = BoaLeituraApplication::class.java.`package`.name
        val importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
            .importPackages(basePackage)
    }

    @Test
    fun applicationPackage_access() {
        Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("Domain").definedBy("$basePackage.domain..")
            .layer("Application").definedBy("$basePackage.application..")
            .layer("Infrastructure").definedBy("$basePackage.infrastructure..")
            .layer("Database").definedBy("$basePackage.infrastructure.database..")
            .layer("Security").definedBy("$basePackage.infrastructure.security..")
            .layer("RestClient").definedBy("$basePackage.infrastructure.rest.client..")
            .layer("RestApi").definedBy("$basePackage.infrastructure.rest.api..")
            .whereLayer("RestClient").mayNotBeAccessedByAnyLayer()
            .whereLayer("RestApi").mayNotBeAccessedByAnyLayer()
            .whereLayer("Database").mayNotBeAccessedByAnyLayer()
            .whereLayer("Security").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
            .ignoreDependency(BoaLeituraApplication::class.java, JwtProperties::class.java)
            .check(importedClasses)
    }

    @Test
    fun useCase_implement() {
        val ruleName = ArchRuleDefinition.classes().that()
            .resideInAnyPackage("$basePackage.application.port.input..")
            .and()
            .resideOutsideOfPackages("..impl")
            .and()
            .areNotMemberClasses()
            .should()
            .haveSimpleNameEndingWith("UseCase")

        ruleName.check(importedClasses)

        val ruleImplemented = ArchRuleDefinition.classes().that()
            .implement(
                DescribedPredicate.and(
                    HasName.Predicates.nameEndingWith("UseCase"),
                    JavaClass.Predicates.resideInAPackage("$basePackage.application.port.input..")
                )
            )
            .should().haveSimpleNameEndingWith("UseCaseImpl")
            .andShould().resideInAPackage("..impl")

        ruleImplemented.check(importedClasses)
    }

    @Test
    fun port_implement() {
        val ruleName = ArchRuleDefinition.classes().that()
            .resideInAnyPackage("$basePackage.application.port.output")
            .and()
            .areNotMemberClasses()
            .should()
            .haveSimpleNameEndingWith("Port")

        ruleName.check(importedClasses)

        val ruleImplementedPort = ArchRuleDefinition.classes().that()
            .implement(
                DescribedPredicate.and(
                    HasName.Predicates.nameEndingWith("Port"),
                    JavaClass.Predicates.resideInAPackage("$basePackage.application.port.output")
                )
            )
            .should().haveSimpleNameEndingWith("Adapter")

        ruleImplementedPort.check(importedClasses)
    }
}