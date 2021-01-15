pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
    resolutionStrategy {
        eachPlugin {
            if (this.requested.id.name == "koin") {
                useModule("org.koin:koin-gradle-plugin:2.2.1")
            }
        }
    }
}
rootProject.name = "HolyGraal"

