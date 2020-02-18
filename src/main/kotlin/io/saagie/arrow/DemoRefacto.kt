package io.saagie.arrow

import io.saagie.arrow.utils.Error
import io.saagie.arrow.utils.OS

fun getPathForKubectl(): String =
    OS.searchBinary("kubectl")

fun downloadAndInstallKubectl(): String = OS.installBinary("kubectl")

fun executeKubectlCmd(kubectlPath: String, context: Map<String, String>) {
    val commandLine = "$kubectlPath get pod ${context.get("podName")} -n ${context.get("namespace")}"
    OS.executeCommandLine(commandLine)
}

val context = mapOf<String, String>(
    "podName" to "mypod-4543G",
    "namespace" to "test"
)

fun main() {
    // will store the kubectl path
    var kubectlPath: String? = null
    try {
        // Search the kubectl program in the computer
        kubectlPath = getPathForKubectl()
    } catch (e: Error) {
        // kubectl is not installed
        // Download and install kubectl
        kubectlPath = downloadAndInstallKubectl()
    }
    // Execute command
    executeKubectlCmd(kubectlPath!!, context)
}
