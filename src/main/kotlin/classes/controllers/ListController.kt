package classes.controllers

import classes.LinkData
import classes.Menu

class ListController {
    private val menu = Menu()

    private val linkData = LinkData("", "", "")
    private val propertyNames =
        linkData.javaClass.declaredFields.map { it.name }

    fun deleteDataInList(list: MutableList<LinkData>) {

        var deleteDataAtIndexUserInput: Int

        if (list.isEmpty()) {
            println("The list is empty! Nothing to delete!\n")
        } else {
            do {
                //itt pl nem feltétlen igaz hogy saved, mert ha az unsavedben akar törölni?
                menu.viewData(list, isSaved = true, isLoaded = true)
                println("You can select between: 1-${list.size}.")
                print("Please select the index where you would like to delete the data: ")

                deleteDataAtIndexUserInput = menu.convertToInt(readln())

                val isDeleteDataAtIndexUserInputValid: Boolean =
                    if (deleteDataAtIndexUserInput > list.size || deleteDataAtIndexUserInput < 1) {
                        println("Bad index! You have provided a number outside of 1-${list.size}.\n")
                        false
                    } else {
                        println("The data at index ${deleteDataAtIndexUserInput}: Link: ${list[deleteDataAtIndexUserInput - 1].link}, Type: ${list[deleteDataAtIndexUserInput - 1].type}, Location: ${list[deleteDataAtIndexUserInput - 1].location}\n")
                        true
                    }
            } while (!isDeleteDataAtIndexUserInputValid)

            for (n in list.indices) {
                if (n == deleteDataAtIndexUserInput - 1) {
                    list.removeAt(n)
                    println("Data at ${n + 1}. was successfully deleted.\n")
                    break
                } else {
                    println("There is no data to delete at this ${n + 1}. index.\n")
                }
            }
        }
    }

    //ÚJ
    fun modifyDataInList(list: MutableList<LinkData>) {
        var modifyDataAtIndexUserInput: Int
        val linkDataTempList = mutableListOf<String>()

        //Ezt hogy lehetne checkolni, hogy üres-e if nélkül és akkor nem az else-be kellene beletenni mindent
        if(list.isEmpty()){
            println("The list is empty!\n")
        }
        else{
            do {
                println("You can select between: 1-${list.size}.")
                print("Please select the index where you would like to modify the data: ")
                modifyDataAtIndexUserInput = menu.convertToInt(readln())

                val isModifyDataAtIndexUserInputValid: Boolean = if (modifyDataAtIndexUserInput > list.size || modifyDataAtIndexUserInput < 1) {
                    println("Bad index! You have provided a number outside of 1-${list.size}.\n")
                    false
                } else {
                    println("The data at index ${modifyDataAtIndexUserInput}: Link: ${list[modifyDataAtIndexUserInput - 1].link}, Type: ${list[modifyDataAtIndexUserInput - 1].type}, Location: ${list[modifyDataAtIndexUserInput - 1].location}\n")
                    true
                }
            } while (!isModifyDataAtIndexUserInputValid)

            outerloop@ do {
                val isModifyDataChoiceUserInputValid: Boolean
                println("Press 0 to cancel.")
                println("1. Modify the whole data at index ${modifyDataAtIndexUserInput}.")
                println("2. Modify only one of the properties at index ${modifyDataAtIndexUserInput}.")
                print("Please select which property you would like to modify: ")
                val modifyDataChoiceUserInput: Int = menu.convertToInt(readln())

                if (modifyDataChoiceUserInput == 0) {
                    println("Cancelled by user!\n")
                    break
                }

                //Modify the whole data
                if (modifyDataChoiceUserInput == 1) {
                    println("1. selected (Modify the whole data at index ${modifyDataAtIndexUserInput}.)\n")
                    println("Press 0 to cancel.")
                    for (i in propertyNames.indices) {
                        print("${menu.firstLetterToUpperCase(propertyNames[i])}: ")
                        val userInput = readln()
                        if (userInput == "0") {
                            println("Cancelled by user.\n")
                            break@outerloop
                        }
                        linkDataTempList.add(i, userInput)
                    }
                    list[modifyDataAtIndexUserInput - 1] =
                        LinkData(linkDataTempList[0], linkDataTempList[1], linkDataTempList[2])
                    println("Successfully modified the whole data at index ${modifyDataAtIndexUserInput}.")
                    println("The new data at index $modifyDataAtIndexUserInput: Link: ${list[modifyDataAtIndexUserInput - 1].link}, Type: ${list[modifyDataAtIndexUserInput - 1].type}, Location: ${list[modifyDataAtIndexUserInput - 1].location}\n")

                    isModifyDataChoiceUserInputValid = true
                }
                //Modify one of the data's property
                else if (modifyDataChoiceUserInput == 2) {
                    println("2. selected (Modify only one of the properties at index ${modifyDataAtIndexUserInput}.)\n")

                    do {
                        println("Press 0 to cancel.")
                        println("You can select between: 1-3.")
                        println("1. Link")
                        println("2. Type")
                        println("3. Location")
                        print("Please select the property which you would like to modify: ")
                        val modifyPropertyUserInput: Int = menu.convertToInt(readln())

                        if (modifyPropertyUserInput == 0) {
                            println("Cancelled by user!\n")
                            break
                        }

                        val isModifyPropertyUserInputValid: Boolean = if (modifyPropertyUserInput > 3 || modifyPropertyUserInput < 1) {
                            println("Bad index! You have provided a number outside of 1-3")
                            false
                        } else {
                            println("Press 0 to cancel.")
                            for (i in propertyNames.indices) {
                                if (i == modifyPropertyUserInput - 1) {
                                    print("${menu.firstLetterToUpperCase(propertyNames[i])}: ")
                                    val userInput = readln()
                                    if (userInput == "0") {
                                        println("Cancelled by user.\n")
                                        break
                                    }

                                    when (i) {
                                        0 -> {
                                            list[modifyDataAtIndexUserInput - 1].link = userInput
                                        }

                                        1 -> {
                                            list[modifyDataAtIndexUserInput - 1].type = userInput
                                        }

                                        2 -> {
                                            list[modifyDataAtIndexUserInput - 1].location = userInput
                                        }

                                        else -> {
                                            println("Bad index!")
                                        }
                                    }
                                }
                            }
                            println("Successfully modified the whole data at index ${modifyDataAtIndexUserInput}.")
                            println("The new data at index $modifyDataAtIndexUserInput: Link: ${list[modifyDataAtIndexUserInput - 1].link}, Type: ${list[modifyDataAtIndexUserInput - 1].type}, Location: ${list[modifyDataAtIndexUserInput - 1].location}\n")
                            true
                        }
                    } while (!isModifyPropertyUserInputValid)

                    isModifyDataChoiceUserInputValid = true
                } else {
                    println("Please select a valid menu point.\n")
                    isModifyDataChoiceUserInputValid = false
                }
            } while (!isModifyDataChoiceUserInputValid)
        }
    }
}