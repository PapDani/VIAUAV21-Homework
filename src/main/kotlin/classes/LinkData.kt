package classes

//class LinkData(var link: String, var type: String, var location: String, var tax: Boolean?) {

//    private fun calculateTax(): Boolean {
//        return when(location) {
//            "US" -> true
//            "EU" -> false
//            "OTHER" -> true
//            "ASIA" -> true
//            else -> throw IllegalArgumentException("Unknown location: $location. Please only use these: US, EU")
//        }
//    }

    //ToDo tax is dependent on the value of location
//    var tax: Boolean = calculateTax()
//}

class LinkData(var link: String, var type: String, var location: String) {
    //Menu.kt a loadData függvényben a contains miatt van rá szükség.
    //ChatGPT magyarázat: The reason why the code adds the data to
    // loadedLinkDataMutableList is that although linkData01 and linkData02
    // have the same values for their properties, they are not the same objects.
    // When you call loadedLinkDataMutableList.contains(linkData) it checks if
    // loadedLinkDataMutableList contains an object that is equal to linkData based on its property values.
    // Since linkData01 and linkData02 are different objects, loadedLinkDataMutableList
    // does not contain linkData02 and the condition !loadedLinkDataMutableList.contains(linkData) is true.
//    With this implementation, loadedLinkDataMutableList.contains(linkData) will check
//    for equality based on the property values and not on the object reference.
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is LinkData) {
            return false
        }
        return this.link == other.link && this.type == other.type && this.location == other.location
    }

//    var tax: Boolean? = when(location){
//        "EU" -> {
//            false
//        }
//        "US" -> {
//            true
//        }
//        "ASIA" -> {
//            true
//        }
//        "OTHER" -> {
//            true
//        }
//        else -> {
//            null
//        }
//    }
}