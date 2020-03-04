package io.saagie.arrow.utils

import java.io.IOException

class FileDownloader {
    companion object {
        @Throws(IOException::class)
        fun getFile(filename: String): List<String> = throw IOException("No network")
    }

}