package io.saagie.arrow.utils

import io.saagie.arrow.utils.Error.ProgramNotFound

class OS {

    companion object {
        @Throws(ProgramNotFound::class)
        fun searchBinary(program: String): String {
            println("\uD83D\uDD0D Looking for $program ...")
            Thread.sleep(2000)
            throw ProgramNotFound(program)
        }

        fun installBinary(program: String): String {
            print("\uD83D\uDCBB Install $program ...")
            Thread.sleep(5000)
            println("Done ✅")
            return "/usr/bin/$program"
        }

        fun executeCommandLine(commandLine: String) {
            println("▶️ $commandLine")
            Thread.sleep(1500)
            println("")
            println("""
                NAME                      READY   STATUS    RESTARTS   AGE
                mypod-4543G               1/1     Running   0          8d
            """.trimIndent())
        }
    }
}
