import arrow.Kind
import arrow.core.*
import arrow.core.extensions.ValidatedApplicative
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative
import io.saagie.arrow.utils.Person
import io.saagie.arrow.utils.ValidationError
import io.saagie.arrow.utils.ValidationError.*

fun <T> T.check(error: ValidationError, predicate: T.() -> Boolean): ValidatedNel<ValidationError, T> =
    if (predicate(this)) {
        this.validNel()
    } else {
        error.invalidNel()
    }

fun validateName(name: String): ValidatedNel<ValidationError, String> =
    ValidatedNel.applicative(Nel.semigroup<ValidationError>()).map(
        name.check(NameTooLong) { length <= 10 },
        name.check(NumberInName) { !contains(Regex("\\d")) }
    ) { name }.fix()

fun validateAge(age: Int): ValidatedNel<ValidationError, Int> =
    ValidatedNel.applicative(Nel.semigroup<ValidationError>()).map(
        age.check(InvalidAge) { this > 0 },
        age.check(TooOldToBeAlive) { this < 140 }
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












// -------------------------------------------------------

fun <T> validator(block: ValidatedApplicative<Nel<ValidationError>>.() -> Kind<ValidatedPartialOf<NonEmptyList<ValidationError>>, T>) =
    block(ValidatedNel.applicative(Nel.semigroup())).fix()

fun validateName2(name: String): ValidatedNel<ValidationError, String> =
    validator {
        map(
            name.check(NameTooLong) { length <= 10 },
            name.check(NumberInName) { !contains(Regex("\\d")) }
        ) { name }
    }
