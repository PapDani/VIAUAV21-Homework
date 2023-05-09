package classes.controllers

import classes.LinkData
import java.io.File
import java.io.IOException

class FileController {

    fun writeToFile(data: MutableList<LinkData>, fileName: String) {

        val file = File(fileName)
        //var count = 1 ha sorszámozni akarom a fileban

        if (file.exists()) {
            println("$fileName already exists! Data will not be overwritten!\n")
            try {
                for (item in data) {
                    //Writing numbers
//                        file.appendText("${count++}. ${item.link}, ${item.type}, ${item.location}\n")
                    //Not writing numbers
                    file.appendText("${item.link}, ${item.type}, ${item.location}\n")
                }
                println("Data successfully added to already existing ${fileName}.\n")
            } catch (e: Exception) {
//                throw Exception("Writing to existing file error: $e")
                throw e
            }
        } else {
            try {
                file.createNewFile()
                println("File was successfully created. File name: ${fileName}.\n")

                file.writeText("Link Type Location\n") //Majd cvs (excel)
                for (item in data) {
                    //Writing numbers
//                        file.appendText("${count++}. ${item.link}, ${item.type}, ${item.location}\n")
                    //Not writing numbers
                    file.appendText("${item.link}, ${item.type}, ${item.location}\n")
                }
                println("Data successfully saved to new file ${fileName}.\n")
            } catch (e: IOException) {
                throw Exception("Creating new file error: $e")
            } catch (e: Exception) {
                throw Exception("Writing to newly created file error: $e")
            }
        }
    }


    fun loadFromFile(pathName: String): MutableList<LinkData> {
        val readDataList = mutableListOf<LinkData>()
        val directory = File(pathName)
        val files = directory.listFiles()

        if (files == null) {
            println("Directory with this path: ˝${pathName}˝ does not exist.\n")
        } else {
            println("List of file names in this directory: ")
            for (file in files) {
                if (file.isFile) {
                    println(file.name)
                }
            }
        }
        //Ezt érdemes lenne a Menu.kt-ba kitenni mielőtt meghívom és akkor lehetne használni a loadFromFile-t a loadFromDirectory-ban?
        print("File name: ")
        val fileName = readln()
        val file = File(fileName)

        if (file.exists()) {
            try {//Ezen belül semmi nem futat hibára? Nem dobhat exception-t, így fölösleges a try????
                val lines = file.readLines().drop(1)
                lines.forEach { line ->
                    val parts = line.split(", ")
                    if (parts.size == 3) {
                        val link = parts[0]
                        val type = parts[1]
                        val location = parts[2]
                        readDataList.add(LinkData(link, type, location))
                    } else {
                        throw Exception("Invalid data format in file: ${file.name}")
                    }
                }
                println("Successfully loaded data from '${fileName}' file!")
                return readDataList
            } catch (e: Exception) {
                throw Exception("Reading from '${fileName}' file error: $e")
            }
        } else {
            throw Exception("File '${fileName}' does not exist or read access is denied!")
        }
    }

    fun readFilesFromDirectory(pathName: String): MutableList<LinkData> {
        val directory = File(pathName)
        val files = directory.listFiles { file -> file.isFile && file.extension == "txt" }
        val readDataList = mutableListOf<LinkData>()

        if (files != null) {
            for (file in files) {
                try {//Ezen belül semmi nem futat hibára? Nem dobhat exception-t, így fölösleges a try????
                    val lines = file.readLines().drop(1)
                    lines.forEach { line ->
                        val parts = line.split(", ")
                        if (parts.size == 3) {
                            val link = parts[0]
                            val type = parts[1]
                            val location = parts[2]
                            readDataList.add(LinkData(link, type, location))
                        } else {
                            throw Exception("Invalid data format in file: ${file.name}")
                        }
                    }
                    println("Successfully loaded data from '${file.name}' file!")
                } catch (e: Exception) {
                    throw Exception("Reading from '${file.name}' file error: $e")
                }
            }
            println()
            return readDataList
        } else {
            throw Exception("Directory does not exist.")
        }
    }

    //Úgy akartam, hogy tudja használni a loadFromFile-ot, hogy kevesebb legyen a kód, de nem sikerült
//    fun readFilesFromDirectory(path: String): MutableList<LinkData> {
//        val directory = File(path)
//        val files = directory.listFiles { file -> file.isFile && file.extension == "txt" }
//        val linkDataList = mutableListOf<LinkData>()
//
//
//        if (files != null) {
//            for (file in files) {
//                try {
//                    val lines = file.readLines().drop(1)
//                    for (line in lines) {
//                        val parts = line.split(", ")
//                        if (parts.size == 3) {
//                            val link = parts[0]
//                            val type = parts[1]
//                            val location = parts[2]
//                            linkDataList.add(LinkData(link, type, location))
//                        } else {
//                            throw Exception("Invalid data format in file: ${file.name}")
//                        }
//                    }
//                    println("Successfully loaded data from ${file.name}\n")
//                    return linkDataList
//                } catch (e: Exception) {
//                    //Ez jó!!!!
////                    println("Error reading file ${file.name}: ${e.message}")
//                    throw Exception("Error reading file ${file.name}: ${e.message}")
//                }
//            }
//        } else {
//            throw Exception("Directory does not exist.")
//        }
//        return TODO("Provide the return value")
//    }
}