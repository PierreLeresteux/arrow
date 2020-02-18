package io.saagie.arrow.utils

sealed class Error : Throwable() {

    data class ProgramNotFound(val programm: String) : Error()
    data class InstallFailed(val programm: String) : Error()
}