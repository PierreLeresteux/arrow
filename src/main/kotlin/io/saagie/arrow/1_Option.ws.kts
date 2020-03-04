import arrow.core.extensions.option.apply.product
import arrow.core.orElse
import arrow.core.toOption
import java.util.*

val myVal : String? =  "abc"
val myBackupVal : String? = "def"

Optional.ofNullable(myVal).let {
    it
        .filter { it.length > 2 }
        .map { it + it }
        .or { Optional.ofNullable(myBackupVal) }
        //.orElse("ghi")
        .ifPresentOrElse(
            { println(it) },
            { println ("no value") }
        )
}

myVal.toOption().let {
    it
        .filter { it.length > 2 }
        .map { it+it }
        .orElse { myBackupVal.toOption() }
        //.getOrElse { "ghi" }
        .fold(
            ifEmpty={ println ("no value") },
            ifSome = { println(it) }
        )
}

myVal.toOption()
    .product(myBackupVal.toOption())
    .map {

    }

