import arrow.fx.IO
import arrow.fx.extensions.io.applicativeError.handleErrorWith
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

private class FileDownloader {
    @Throws(IOException::class)
    fun getFile(filename: String): List<String> = throw IOException("No network")
}

fun loadRemoteData(): IO<List<String>> = IO {
    FileDownloader().getFile("data.csv")
}

fun loadLocalData(): IO<List<String>> = IO {
    Files.readAllLines(Paths.get("data.csv"))
}

fun loadCacheData() = IO {
    "this is cache data"
}

loadRemoteData()
    .handleErrorWith { loadLocalData() }
    .handleErrorWith { loadCacheData() }
    .attempt()
    .unsafeRunSync()
