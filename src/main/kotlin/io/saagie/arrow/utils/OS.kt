package io.saagie.arrow.utils

import io.saagie.arrow.utils.Error.ProgramNotFound
import kotlin.random.Random

class OS {

    companion object {
        @Throws(ProgramNotFound::class)
        fun searchBinary(program: String): String {
            print("\uD83D\uDD0D Looking for $program ...")
            Thread.sleep(2000)
            if (Random.nextBoolean()) {
                println("Not found \uD83D\uDCA5")
                throw ProgramNotFound(program)
            } else {
                println("Found ✅")
                return "/usr/bin/kubectl"
            }

        }

        fun installBinary(program: String): String {
            print("\uD83D\uDCBB Install $program ...")
            Thread.sleep(2000)

            if (Random.nextBoolean()) {
                println("Failed \uD83D\uDCA5")
                throw Error.InstallFailed(program)
            } else {
                println("Done ✅")
                return "/usr/bin/$program"
            }
        }

        fun executeCommandLine(commandLine: String) {
            println("▶️ $commandLine")
            Thread.sleep(1500)
            println("")
            println(
                """
                NAME                      READY   STATUS    RESTARTS   AGE
                mypod-4543G               1/1     Running   0          8d
            """.trimIndent()
            )
        }
    }
}
