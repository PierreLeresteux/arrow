import arrow.core.*
import arrow.core.extensions.listk.semigroup.semigroup
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative
import arrow.core.extensions.validated.foldable.fold

sealed class ValidationError(val description : String) {
    object NameTooLong : ValidationError("Name is too long")
    object NumberInName : ValidationError("Name contains a number")
    object InvalidAge : ValidationError("Age is not a number")
    object TooOldToBeAlive : ValidationError("Nobody can live that long!")
}

data class Person(
    val name : String,
    val age : Int
)



fun validateName(name :String) : ValidatedNel<ValidationError, String> {
    val errors = mutableListOf<ValidationError>()
    if (name.length > 10) errors.add(ValidationError.NameTooLong)
    if (name.contains(Regex("\\d"))) errors.add(ValidationError.NumberInName)
    return Nel.fromList(errors).fold(
        { name.valid() },
        { it.invalid()}
    )
}



fun validateAge(age :Int) : ValidatedNel<ValidationError, Int>  {
    val errors = mutableListOf<ValidationError>()
    if (age < 0) errors.add(ValidationError.InvalidAge)
    if (age >= 140) errors.add(ValidationError.TooOldToBeAlive)
    return Nel.fromList(errors).fold(
        { age.valid() },
        { it.invalid()}
    )
}


fun Person.validate()  =
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
    name="a",
    age = 2
).validate()

Person(
    name="a1fqdsfgqdsg",
    age = 220
).validate()
