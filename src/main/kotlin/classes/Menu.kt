package classes

import java.util.*

class Menu {

    private val linkData = LinkData("", "", "")
    private val propertyNames =
        linkData.javaClass.declaredFields.map { it.name }

    //Main Menu display
    fun displayMainMenu() {
        println("1. Add data")
        println("2. View data")
        println("3. Save data")
        println("4. Load data")
        println("5. Delete data")
        println("6. Modify data")
        println("0. Exit\n")
    }

    //First letter of string to uppercase
    fun firstLetterToUpperCase(s: String): String {
        return s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    //ÚJ
    //Reading user input for menu navigation from console
    fun convertToInt(userInput: String): Int {
        try {
            val number = userInput.toInt()
            if (!userInput.matches(Regex("-?\\d+"))) {
                throw IllegalArgumentException("Input must be a number")
            }
            println("User input: $number\n")
            return number
        } catch (e: NumberFormatException) {
            println("User input: $userInput\n")
            throw IllegalArgumentException("Input must be a number")
        }
    }

    fun viewData(data: MutableList<LinkData>, isSaved: Boolean, isLoaded: Boolean) {
        //Checks if the main list is empty
        if (data.isEmpty()) {
            if (isSaved && isLoaded) {
                println("There is no loaded data to display!\n")
            } else if (isSaved) {
                println("There is no saved data to display!\n")
            } else {
                println("There is no unsaved data to display!\n")
            }
            //Prints out how many records there are
        } else {
            if (isSaved && isLoaded) {
                println("There ${if (data.size == 1) "is" else "are"} ${data.size} loaded record${if (data.size == 1) "" else "s"}:\n")
            } else if (isSaved) {
                println("There ${if (data.size == 1) "is" else "are"} ${data.size} saved record${if (data.size == 1) "" else "s"}:\n")
            } else {
                println("There ${if (data.size == 1) "is" else "are"} ${data.size} unsaved record${if (data.size == 1) "" else "s"}:\n")
            }

            var quantity = 0

            //ToDo Hogy tudnám úgy kiíratni, hogy akkor is jól működjön és ne kelljen kiegészíteni, ha plusz propertije lesz a class-nak?
            for (linkData in data) {
                quantity++
                println(
                    "${quantity}. " + "${firstLetterToUpperCase(propertyNames[0])}: ${linkData.link}, " + "${
                        firstLetterToUpperCase(
                            propertyNames[1]
                        )
                    }: ${linkData.type}, " + "${firstLetterToUpperCase(propertyNames[2])}: ${linkData.location}"
                )
            }
            print("\n")
        }
    }
}