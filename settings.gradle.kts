// Standalone build of the library (for its own tests and CI). An app that
// uses the library as a git submodule includes only `:marmalade-ui` from its
// own settings; see README.md.
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MarmaladeUI"
include(":marmalade-ui")
