package classes

import classes.controllers.FileController
import classes.controllers.ListController

class CRUD {
    private val menu = Menu()
    private val fileController = FileController()
    private val listController = ListController()

    private val linkData = LinkData("", "", "")
    private val propertyNames =
        linkData.javaClass.declaredFields.map { it.name }//.dropLast(1) //csak akkor kell ha a tax is ott lesz és az számolva lesz, ezért nem kell kiíratni

    fun addData(savedLinkDataMutableList: MutableList<LinkData>, unSavedLinkDataMutableList: MutableList<LinkData>) {
        val linkDataTempList = mutableListOf<String>()

        /* ToDo whenever user presses 0, it comes back to the main menu */
        //2 MÁSODLAGOS It works but only with pressing enter after entering 0. Below, in the comment there is a possible solution:
        /*You can use the readLine() function in Kotlin to read input from the console. This function waits for the user to press the enter key after typing the input, which might not be exactly what you're looking for.

        However, if you want to continuously read input from the console as the user types, you can use a library like readline or jline3. Here is an example using the readline library:

        1. Add the readline dependency to your project. If you're using Gradle, add the following to your build.gradle file:

        dependencies {
            implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk8'
            implementation 'com.github.ajalt:readline:0.8.0'
        }
        2. Use the readline library to read input from the console:

        import com.github.ajalt.readline.Readline

        fun main() {
            val readline = Readline()

            while (true) {
                val input = readline.readline()
                println("You typed: $input")
            }
        }

        In this example, the readline() function from the readline library is used to continuously read input from the console. The while loop will keep running until the program is terminated (for example, by pressing Ctrl+C). The user's input is printed to the console as soon as they type something.

        Note that using a library like readline will give you more control over the input reading process, but might also introduce additional complexity to your code.
        */

//        TODO("pl deletenél ilyenre megcsinálni a userinputot, mert ha hibát kap, egyel kijebb dob")
        outerloop@ do {
            println("Press 0 and after that 'Enter' to cancel.")

            for (i in propertyNames.indices) {
                print("${menu.firstLetterToUpperCase(propertyNames[i])}: ")
                val userInput = readln()
                //Checks user input in this for loop. If it's 0 then it exits the for loop as well as the outer, do-while loop
                if (userInput == "0") {
                    println("Cancelled by user.\n")
                    break@outerloop
                }
                linkDataTempList.add(i, userInput)
            }

            do {
                //Itt miért nem dobódik ki a főmenübe, ha betűket adok meg?
                print("Would you like to save the data to a file? 1. Yes/2. No: ")

                var userInputSaveData = 0
                //Kell a try??? Át kellene írni a readUserInputForMenu-t?
                try {
                    userInputSaveData = menu.convertToInt(readln())
                } catch (e: Exception) {
                    println("$e\n")
                    continue //It will continue the do loop
                }

                when (userInputSaveData) {
                    1 -> {
                        println("1. selected (Yes)\n")
                        unSavedLinkDataMutableList.add(
                            LinkData(
                                linkDataTempList[0], linkDataTempList[1], linkDataTempList[2]
                            )
                        )
                        saveData(savedLinkDataMutableList, unSavedLinkDataMutableList)
                        linkDataTempList.clear()
                        break
                    }

                    2 -> {
                        unSavedLinkDataMutableList.add(
                            LinkData(
                                linkDataTempList[0], linkDataTempList[1], linkDataTempList[2]
                            )
                        )
                        linkDataTempList.clear()
                        println("2. selected (No)\n")
                    }

                    else -> {
                        println("Please input a valid option, 1 or 2.")
                    }
                }
            } while (userInputSaveData != 2)

        } while (linkDataTempList.isNotEmpty())
    }

    fun saveData(savedLinkDataMutableList: MutableList<LinkData>, unSavedLinkDataMutableList: MutableList<LinkData>) {

        if (unSavedLinkDataMutableList.isEmpty()) {
            println("There is no data to be saved!\n")
        } else {
            print("File name: ")
            val fileName = readln()
            fileController.writeToFile(unSavedLinkDataMutableList, fileName)
            savedLinkDataMutableList.addAll(unSavedLinkDataMutableList)
            unSavedLinkDataMutableList.clear()
        }
    }

    fun loadData(loadedLinkDataMutableList: MutableList<LinkData>) {

        do {
            println("1. Load data from one file")
            println("2. Load files from a directory")
            println("0. Cancel") //Meggondolni, hogy még hol lehetne alkalmazni!

            //ezt külön függvénybe?, Miért nem működik úgy, mint az addDatában?
            var userInputLoadData = 0
            try {
                userInputLoadData = menu.convertToInt(readln())
            } catch (e: Exception) {
                println("$e\n")
                continue //It will continue the do loop
            }

            when (userInputLoadData) {
                1 -> {
                    //Amikor akartam, hogy a loadFromfile-t haszználja a loadFromDirectori akkor jó volt, de most vissza tettem a loadFromFile-ba
//                    print("File name: ")
//                    val fileName = readln()
                    //User adja meg a path-t?
                    val loadedLinkDataMutableListTemp: MutableList<LinkData> =
                        fileController.loadFromFile("C:\\Users\\papda\\IdeaProjects\\Beadando_teszt_map_elott\\db")
//                    loadedLinkDataMutableListTemp.addAll(fileController.loadFromFile("C:\\Users\\papda\\IdeaProjects\\Beadando_teszt_map_elott\\db"))
                    if (loadedLinkDataMutableListTemp.isEmpty()) {
                        println("There is no data in the file!")
                        break
                    } else {
                        for (linkData in loadedLinkDataMutableListTemp) {
                            if (!loadedLinkDataMutableList.contains(linkData)) {
                                loadedLinkDataMutableList.add(linkData)
                                println("This data: ${linkData.link}, ${linkData.type}, ${linkData.location} was successfully added to the LoadedDataList.")
                            } else {
                                println("This data: ${linkData.link}, ${linkData.type}, ${linkData.location} is already loaded.")
                            }
                        }
                        //Ide meghívni a kiírató függvényt?
//                        println("Data loaded from the file:\n")
//                        for (linkData in loadedLinkDataMutableList) {
//                            println("Link: ${linkData.link}, Type: ${linkData.type}, Location: ${linkData.location}\n")
//                        }
                        menu.viewData(loadedLinkDataMutableList, isSaved = true, isLoaded = true)
                    }
                    loadedLinkDataMutableListTemp.clear()
                    break
                }

                2 -> {
                    //User adja meg a path-t?
                    val loadedLinkDataMutableListTemp: MutableList<LinkData> =
                        fileController.readFilesFromDirectory("C:\\Users\\papda\\IdeaProjects\\Beadando_teszt_map_elott\\db")

                    if (loadedLinkDataMutableListTemp.isEmpty()) {
                        println("There is no data in the files!")
                        break
                    } else {
                        for (linkData in loadedLinkDataMutableListTemp) {
                            if (!loadedLinkDataMutableList.contains(linkData)) {
                                loadedLinkDataMutableList.add(linkData)
                                println("This data: ${linkData.link}, ${linkData.type}, ${linkData.location} was successfully added to the LoadedDataList.")
                            } else {
                                println("This data: ${linkData.link}, ${linkData.type}, ${linkData.location} is already loaded.")
                            }
                        }
                        println()
                        //Ide meghívni a kiírató függvényt?
//                        println("Data loaded from the file:\n")
//                        for (linkData in loadedLinkDataMutableList) {
//                            println("Link: ${linkData.link}, Type: ${linkData.type}, Location: ${linkData.location}\n")
//                        }
                        menu.viewData(loadedLinkDataMutableList, isSaved = true, isLoaded = true)
                    }
                    loadedLinkDataMutableListTemp.clear()
                    break
                }

                0 -> { //Meggondolni, hogy még hol lehetne alkalmazni!
                    println("Cancelled by user!")
                    break
                }

                else -> {
                    println("Please input a valid option, 1. or 2.")
                }
            }
        } while (userInputLoadData != 0)
    }

    fun deleteData(
        savedLinkDataMutableList: MutableList<LinkData>,
        unSavedLinkDataMutableList: MutableList<LinkData>,
        loadedLinkDataMutableList: MutableList<LinkData>
    ) {
        do {
            println("1. Delete data from Saved Links list")
            println("2. Delete data from Unsaved Links list")
            println("3. Delete data from Loaded Links list")
            println("4. Delete all of the data from a list")
            println("0. Cancel")
            print("Please choose a option: ")


            val userInput = menu.convertToInt(readln())
            var isChooseDeleteMethodCancelledByUser = false
            if (userInput == 0) {
                isChooseDeleteMethodCancelledByUser = true
            }

            when (userInput) {
                1 -> {
                    //Szerintem egész ilyen mehet külön függvénybe, hova szervezzem ki?
//                    TODO("delete a specific data from savedLinkDataMutableList")
                    listController.deleteDataInList(savedLinkDataMutableList)
                    break
                }

                2 -> {
//                    TODO("delete a specific data from unSavedLinkDataMutableList")
                    listController.deleteDataInList(unSavedLinkDataMutableList)
                    break
                }

                3 -> {
//                    TODO("delete a specific data from loadedLinkDataMutableList")
                    listController.deleteDataInList(loadedLinkDataMutableList)
                    break
                }

                4 -> {
//                    TODO("delete all of data from the list the user chooses")
                    do {
                        println("1. Saved links list")
                        println("2. Unsaved links list")
                        println("3. Loaded links list")
                        println("0. Cancel")
                        print("Which list would you like to delete? ")


                        val userInputDeleteData = menu.convertToInt(readln())
                        var isChooseListCancelledByUser = false
                        if (userInputDeleteData == 0) {
                            isChooseListCancelledByUser = true
                        }

                        when (userInputDeleteData) {
                            1 -> {
                                if (savedLinkDataMutableList.isEmpty()) {
                                    println("Saved links list is empty. Nothing to delete!\n")
                                } else {
                                    savedLinkDataMutableList.clear()
                                    println("Successfully deleted all data from Saved links list!\n")
                                }
                                break
                            }

                            2 -> {
                                if (unSavedLinkDataMutableList.isEmpty()) {
                                    println("Unsaved links list is empty. Nothing to delete!\n")
                                } else {
                                    unSavedLinkDataMutableList.clear()
                                    println("Successfully deleted all data from Unsaved links list!\n")
                                }
                                break
                            }

                            3 -> {
                                if (loadedLinkDataMutableList.isEmpty()) {
                                    println("Loaded links list is empty. Nothing to delete!\n")
                                } else {
                                    loadedLinkDataMutableList.clear()
                                    println("Successfully deleted all data from Unsaved links list!\n")
                                }
                                break
                            }

                            0 -> {
                                println("Cancelled by user!")
                            }

                            else -> {
                                println("Please input a valid option, 0, 1, 2 or 3.")
                            }
                        }
                    } while (!isChooseListCancelledByUser)
                    break
                }

                0 -> {
                    println("Cancelled by user!\n")
                }

                else -> {
                    println("Please input a valid option, 0, 1, 2, 3 or 4\n")
                }
            }
        } while (!isChooseDeleteMethodCancelledByUser)
    }

    fun modifyData(
        savedLinkDataMutableList: MutableList<LinkData>,
        unSavedLinkDataMutableList: MutableList<LinkData>,
        loadedLinkDataMutableList: MutableList<LinkData>
    ) {

//        TODO("pl deletenél ilyenre megcsinálni a userinputot, mert ha hibát kap, egyel kijebb dob")
        var isCancelledByUser = false
        var userInputModifyData: Int
        //Mi a faszom a különbség eközött és a Menu.kt-ban a 91-től 100-ig?????
        do {
            println("Press 0 to cancel.")
            println("1. Modify data from Saved Links list")
            println("2. Modify data from Unsaved Links list")
            println("3. Modify data from Loaded Links list")
            print("Which list would you like to modify data in? ")

            userInputModifyData = menu.convertToInt(readln())

            if (userInputModifyData == 0) {
                isCancelledByUser = true
            }

            when (userInputModifyData) {
                1 -> {
                    println("1. Modify data from Saved Links list")
                    listController.modifyDataInList(savedLinkDataMutableList)
                    break
                }

                2 -> {
                    println("2. Modify data from Unsaved Links list")
                    listController.modifyDataInList(unSavedLinkDataMutableList)
                    break
                }

                3 -> {
                    println("3. Modify data from Loaded Links list")
                    listController.modifyDataInList(loadedLinkDataMutableList)
                    break
                }

                0 -> {
                    println("Cancelled by user.\n")
                }

                else -> {
                    println("Please input a valid option, 0, 1, 2 or 3.\n")
                }
            }
        } while (!isCancelledByUser)
    }
}
