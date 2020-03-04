import arrow.core.Either
import arrow.core.handleErrorWith
import arrow.core.right
import arrow.fx.extensions.io.applicativeError.handleErrorWith
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlinx.coroutines.*

private class FileDownloader {
    @Throws(IOException::class)
    fun getFile(filename: String): List<String> = throw IOException("No network")
}

fun loadRemoteData(): Either<Throwable, List<String>> = runBlocking {
    Either.catch({ IOException("Unable to get remote data", it) }, {
        FileDownloader().getFile("data.csv")
    })
}

fun loadLocalData(): Either<Throwable, List<String>> = runBlocking {
    Either.catch({ IOException("Unable to get local data", it) }, {
        Files.readAllLines(Paths.get("data.csv"))
    })
}

fun loadCacheData() = listOf("this is cache data").right()

loadRemoteData()
    .handleErrorWith { loadLocalData() }
    .handleErrorWith { loadCacheData() }
    .fold({ println(it) }, { println(it) })
