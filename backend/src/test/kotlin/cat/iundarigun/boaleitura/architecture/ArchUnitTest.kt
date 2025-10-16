package cat.iundarigun.boaleitura.architecture

import cat.iundarigun.boaleitura.BoaLeituraApplication
import cat.iundarigun.boaleitura.infrastructure.database.ImportService
import cat.iundarigun.boaleitura.infrastructure.rest.api.controller.ImportController
import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.base.DescribedPredicate.not
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo
import com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.conditions.ArchConditions
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.Test

class ArchUnitTest {
    companion object {
        val basePackage = BoaLeituraApplication::class.java.`package`.name
        val importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
//            .withImportOption { !it.contains("extensions") }
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
            .layer("RestClient").definedBy("$basePackage.infrastructure.rest.client..")
            .layer("RestApi").definedBy("$basePackage.infrastructure.rest.api..")
            .whereLayer("RestClient").mayNotBeAccessedByAnyLayer()
            .whereLayer("RestApi").mayNotBeAccessedByAnyLayer()
            .whereLayer("Database").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers( "Application", "Infrastructure")
            .check(importedClasses
                .that(not(equivalentTo(ImportService::class.java))))
    }
}