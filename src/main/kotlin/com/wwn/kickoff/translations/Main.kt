package com.wwn.kickoff.translations

import java.io.File

/**
* @author Nivaldo Bondança
*/

fun main(args: Array<String>) {
    val android = File("../kickoff-android/Kickoff-new/src/main/res")
    val oneSky = File("../translations_strings/")
    val mapping = File("../kickoff-android/translations/StringKeyMap.json")

    val languages = arrayOf("values", "values-es", "values-pt-rBR")
//    val languages = arrayOf("values")

    Translator(languages, android, oneSky, mapping).run()
}
