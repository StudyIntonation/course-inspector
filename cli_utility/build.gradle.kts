import org.gradle.jvm.tasks.Jar

version = "1.0"

plugins {
    application
}

val mainClass = "org.studyintonation.course_inspector.cli_utility.CLIUtility"

application {
    mainClassName = mainClass

    tasks {
        startScripts {
            applicationName = "inspector"
        }
    }
}

dependencies {
    compile("com.google.code.gson:gson:2.8.6")

    compile(project(":inspector"))
    compile(project(":info_generator"))
}

distributions {
    tasks {
        distZip {
            archiveFileName.set("inspector.zip")
        }
        distTar {
            archiveFileName.set("inspector.tar")
        }
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    archiveBaseName.set("${rootProject.name}-${project.name}")
    manifest {
        attributes["Implementation-Title"] = "Inspector CLI utility"
        attributes["Implementation-Version"] = archiveVersion.get()
        attributes["Main-Class"] = mainClass
    }
    from(configurations.compile.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
