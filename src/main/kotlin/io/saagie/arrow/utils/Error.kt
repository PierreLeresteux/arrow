package io.saagie.arrow.utils

sealed class Error : Throwable() {

    data class ProgramNotFound(val programm: String) : Error()
    data class InstallFailed(val programm: String) : Error()
}

sealed class ValidationError(val description: String) {
    object NameTooLong : ValidationError("Name is too long")
    object NumberInName : ValidationError("Name contains a number")
    object InvalidAge : ValidationError("Age is not a number")
    object TooOldToBeAlive : ValidationError("Nobody can live that long!")
}
