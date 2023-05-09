//My imports
import classes.CRUD
import classes.LinkData
import classes.Menu

fun main() {
     val savedLinkDataMutableList = mutableListOf<LinkData>()
     val unSavedLinkDataMutableList = mutableListOf<LinkData>()
     val loadedLinkDataMutableList = mutableListOf<LinkData>()

    var menuNavigationValue: Int? = null
    //Constructors
    val menu = Menu()
    val crud = CRUD()

    //Do While
    do{
        try {
            //Menu display
            menu.displayMainMenu()

            //Read user input for menu navigation
            print("Please select a menu point: ")

            menuNavigationValue = menu.convertToInt(readln())

            //To see if it gets the expected value before entering the 'when'
            println("When előtt a menuNavigationValue: $menuNavigationValue")

            //Menu logic
            when (menuNavigationValue) {
//                -1 -> println("User Input Error\n")
                0 -> println("0. selected (Exit)\n")

                1 -> {
                    println("1. selected (Add data)\n")
                    crud.addData(savedLinkDataMutableList, unSavedLinkDataMutableList)
                }

                2 -> {
                    println("2. selected (View data)\n")
                    menu.viewData(savedLinkDataMutableList, isSaved = true, isLoaded = false)
                    menu.viewData(loadedLinkDataMutableList, isSaved = true, isLoaded = true)
                    menu.viewData(unSavedLinkDataMutableList, isSaved = false, isLoaded = false)
                }

                3 -> {
                    println("3. selected (Save data)\n")
                    menu.viewData(unSavedLinkDataMutableList, isSaved = false, isLoaded = false)
                    crud.saveData(savedLinkDataMutableList, unSavedLinkDataMutableList)
                }

                4 -> {
                    println("4. selected (Load data)\n")
                    crud.loadData(loadedLinkDataMutableList)
                }

                5 -> {
                    println("5. selected (Delete data)\n")
                    crud.deleteData(savedLinkDataMutableList, unSavedLinkDataMutableList, loadedLinkDataMutableList)
                }

                6 -> {
                    println("6. selected (Modify data)\n")
                    crud.modifyData(savedLinkDataMutableList, unSavedLinkDataMutableList, loadedLinkDataMutableList)
                }

                else -> {
                    println("Please select a valid menu point.\n")
                }
            }
        }catch (e: Exception){
            //Ezt nem itt elkapni, hogy ne dobjon ki ide, fő menübe ha betűt adok meg inputnak
            println("Dani exceptions: $e\n")
        }
        //Menu logic end
    }while(menuNavigationValue != 0)
    //Do While end

    println("Program end")
}