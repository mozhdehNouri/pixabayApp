pluginManagement {
    repositories {
            maven {
                url =
                    uri("https://nexus.samentic.com/repository/samentic-android")
                credentials {
                    username = System.getenv("SAMENTIC_NEXUS_USERNAME")!!
                    password = System.getenv("SAMENTIC_NEXUS_PASSWORD")!!
                }
            }
                google()
                mavenCentral()
                gradlePluginPortal()
            }
        }


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url =
                uri("https://nexus.samentic.com/repository/samentic-android")
            credentials {
                username = System.getenv("SAMENTIC_NEXUS_USERNAME")!!
                password = System.getenv("SAMENTIC_NEXUS_PASSWORD")!!
            }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "PixabayApp"
include(":app")
 