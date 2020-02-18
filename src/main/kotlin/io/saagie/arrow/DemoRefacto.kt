package io.saagie.arrow

import io.saagie.arrow.utils.Error
import io.saagie.arrow.utils.OS
import kotlinx.coroutines.runBlocking

suspend fun getPathForKubectl(): String = OS.searchBinary("kubectl")

suspend fun downloadAndInstallKubectl(): String = OS.installBinary("kubectl")

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
        // will store the kubectl path
        var kubectlPath: String? = null
        try {
            // Search the kubectl program in the computer
            kubectlPath = getPathForKubectl()
        } catch (e: Error) {
            // kubectl is not installed
            println("    ⚠️ `kubectl` not found ...")
            println("    ⚙️ Will install it")
            // Download and install kubectl
            kubectlPath = downloadAndInstallKubectl()
        }
        // Execute command
        executeKubectlCmd(kubectlPath!!, context)
    }
}
