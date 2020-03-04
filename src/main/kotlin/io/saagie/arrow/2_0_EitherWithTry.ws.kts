import arrow.core.Either
import arrow.core.Try
import arrow.core.handleErrorWith
import arrow.core.right
import io.saagie.arrow.utils.FileDownloader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


fun loadRemoteData(): Either<Throwable, List<String>> = Try {
    FileDownloader.getFile("data.csv")
}.toEither { IOException("Unable to get remote data", it) }

fun loadLocalData(): Either<Throwable, List<String>> = Try {
    Files.readAllLines(Paths.get("data.csv"))
}.toEither { IOException("Unable to get local data", it) }

fun loadCacheData() = listOf("this is cache data").right()

loadRemoteData()
    .handleErrorWith { loadLocalData() }
    .handleErrorWith { loadCacheData() }
    .fold({ println(it) }, { println(it) })
