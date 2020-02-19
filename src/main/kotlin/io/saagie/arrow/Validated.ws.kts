import arrow.core.*
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative
import io.saagie.arrow.utils.Person
import io.saagie.arrow.utils.ValidationError
import io.saagie.arrow.utils.ValidationError.*

fun validateName(name: String): ValidatedNel<ValidationError, String> {
    val errors = mutableListOf<ValidationError>()
    if (name.length > 10) errors.add(NameTooLong)
    if (name.contains(Regex("\\d"))) errors.add(NumberInName)
    return Nel.fromList(errors).fold(
        { name.valid() },
        { it.invalid() }
    )
}

fun validateAge(age: Int): ValidatedNel<ValidationError, Int> {
    val errors = mutableListOf<ValidationError>()
    if (age < 0) errors.add(InvalidAge)
    if (age >= 140) errors.add(TooOldToBeAlive)
    return Nel.fromList(errors).fold(
        { age.valid() },
        { it.invalid() }
    )
}

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
