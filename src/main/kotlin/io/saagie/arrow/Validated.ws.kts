import arrow.core.*
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative
import io.saagie.arrow.utils.Person
import io.saagie.arrow.utils.ValidationError
import io.saagie.arrow.utils.ValidationError.*

fun <T> T.validate(error: ValidationError, block: T.() -> Boolean): ValidatedNel<ValidationError, T> =
    if (block(this)) {
        this.validNel()
    } else {
        error.invalidNel()
    }

fun validateName(name: String): ValidatedNel<ValidationError, String> =
    ValidatedNel.applicative(Nel.semigroup<ValidationError>()).map(
        name.validate(NameTooLong) { length <= 10 },
        name.validate(NumberInName) { !contains(Regex("\\d")) }
    ) { name }.fix()

fun validateAge(age: Int): ValidatedNel<ValidationError, Int> =
    ValidatedNel.applicative(Nel.semigroup<ValidationError>()).map(
        age.validate(InvalidAge) { this > 0 },
        age.validate(TooOldToBeAlive) { this < 140 }
    ) { age }.fix()

fun Person.validate() =
    ValidatedNel.applicative(Nel.semigroup<ValidationError>()).map(
        validateName(name),
        validateAge(age)
    ) { (name, age) ->
        println("Name $name and age $age are both valid")
    }.handleLeftWith {
        println(it.map(ValidationError::description))
        it.invalid()
    }

Person(
    name = "a",
    age = 2
).validate()

Person(
    name = "a1fqdsfgqdsg",
    age = 220
).validate()
