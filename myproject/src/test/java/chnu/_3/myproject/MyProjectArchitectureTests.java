package chnu._3.myproject;/*
  @author Bogdan
  @project myproject
  @class MyProjectArchitectureTests
  @version 1.0.0
  @since 19.04.2025 - 23.03
*/

import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import chnu._3.myproject.model.Duck;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

public class MyProjectArchitectureTests {

    private JavaClasses applicationClasses;

    @Test
    void controllersShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(RestController.class)
                .check(applicationClasses);
    }

    @Test
    void controllerClassesShouldBeNamedXController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should()
                .haveSimpleNameEndingWith("Controller")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldNotDependOnOtherControllers() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldNotDependOnServices() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldBeAnnotatedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .beAnnotatedWith(Service.class)
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeInterfaces() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .beInterfaces()
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldNotDependOnServices() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void modelFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat()
                .resideInAPackage("..model..")
                .should().notBePublic()
                .check(applicationClasses);
    }

    @Test
    void shouldNotHaveCyclicDependencies() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void serviceClassesShouldBeNamedXService() {
        classes()
                .that().resideInAPackage("..service..")
                .should()
                .haveSimpleNameEndingWith("Service")
                .check(applicationClasses);
    }

    @Test
    void repositoryClassesShouldBeNamedXRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should()
                .haveSimpleNameEndingWith("Repository")
                .check(applicationClasses);
    }

    @Test
    void controllerFieldsShouldNotBeAutowired() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(applicationClasses);
    }

    @Test
    void modelsShouldBeSerializable() {
        classes()
                .that().areAnnotatedWith(RestController.class)
                .should()
                .haveOnlyFinalFields()
                .check(applicationClasses);
    }

    @Test
    void modelClassesShouldHavePrivateFields() {
        fields()
                .that().areDeclaredInClassesThat()
                .resideInAPackage("..model..")
                .should().notBePublic()
                .check(applicationClasses);
    }

    @Test
    void controllersShouldNotDependOnServicesOrOtherControllers() {
        noClasses()
                .that().resideInAPackage("..controller..") // шукаємо класи в пакеті controller
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..service..", "..controller..") // не повинні залежати від класів з пакетів service та controller
                .because("Контролери не повинні залежати від сервісів чи інших контролерів")
                .check(applicationClasses);
    }
}
