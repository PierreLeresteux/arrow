package io.saagie.arrow.either

import arrow.core.Either
import arrow.core.handleErrorWith
import io.saagie.arrow.utils.Error
import io.saagie.arrow.utils.Error.InstallFailed
import io.saagie.arrow.utils.Error.ProgramNotFound
import io.saagie.arrow.utils.OS
import kotlinx.coroutines.runBlocking

suspend fun getPathForKubectl(): Either<Error, String> =
    Either.catch {
        OS.searchBinary("kubectl")
    }.mapLeft { ProgramNotFound("kubectl") }

suspend fun downloadAndInstallKubectl(): Either<Error, String> =
    Either.catch {
        OS.installBinary("kubectl")
    }.mapLeft { InstallFailed("kubectl") }

suspend fun executeKubectlCmd(kubectlPath: String, context: Map<String, String>) {
    val commandLine = "$kubectlPath get pod ${context.get("podName")} -n ${context.get("namespace")}"
    OS.executeCommandLine(commandLine)
}

val context = mapOf<String, String>(
    "podName" to "mypod-4543G",
    "namespace" to "test"
)

fun main() {
    runBlocking {
        getPathForKubectl()
            .handleErrorWith {
                runBlocking {
                    downloadAndInstallKubectl()
                }
            }
            .fold({
                println("ERROR : $it")
            }, { kubectlPath ->
                executeKubectlCmd(kubectlPath, context)
            })

    }
}

