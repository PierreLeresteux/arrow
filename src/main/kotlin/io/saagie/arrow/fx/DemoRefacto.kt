package io.saagie.arrow.fx

import arrow.fx.IO
import arrow.fx.handleErrorWith
import io.saagie.arrow.utils.OS

fun getPathForKubectl() =
    IO {
        OS.searchBinary("kubectl")
    }

fun downloadAndInstallKubectl() =
    IO {
        OS.installBinary("kubectl")
    }

fun executeKubectlCmd(kubectlPath: String, context: Map<String, String>) {
    val commandLine = "$kubectlPath get pod ${context.get("podName")} -n ${context.get("namespace")}"
    OS.executeCommandLine(commandLine)
}

val context = mapOf<String, String>(
    "podName" to "mypod-4543G",
    "namespace" to "test"
)

fun main() {
    getPathForKubectl()
        .handleErrorWith { downloadAndInstallKubectl() }
        .unsafeRunAsync() {
            it.fold(
                ifLeft = { println("ERROR $it") },
                ifRight = { kubePath -> executeKubectlCmd(kubePath, io.saagie.arrow.context) }
            )
        }
}

