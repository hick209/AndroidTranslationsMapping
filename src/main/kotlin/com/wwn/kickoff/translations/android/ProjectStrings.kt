package com.wwn.kickoff.translations.android

import com.wwn.kickoff.translations.mapping.MappingResults
import com.wwn.kickoff.translations.xml.ResourcesXmlProcessor
import com.wwn.kickoff.translations.xml.ResourcesXmlWriter
import com.wwn.kickoff.translations.xml.XmlLine
import java.io.File

/**
 * @author Nivaldo Bondança
 */
class ProjectStrings(androidResourcesDir: File) {

    private val androidResourcesDir = androidResourcesDir

    private var newStrings: ResourcesXmlWriter? = null
    private var keyToFile: MutableMap<String, ResourcesXmlWriter> = hashMapOf()

    fun prepareLanguage(language: String) {
        val values = File(androidResourcesDir, language)
        val stringsFiles = values.listFiles { file, s -> s.startsWith("strings_") }

        newStrings = ResourcesXmlWriter(File(values, "strings_new.xml"), xmlLines = emptyList())
        keyToFile.clear()

        for (file in stringsFiles) {
            val xmlLines = ResourcesXmlProcessor(file).read()
            val writer = ResourcesXmlWriter(file, xmlLines)
            for (xmlLine in xmlLines) {
                keyToFile.put(xmlLine.key, writer)
            }
        }
    }

    fun process(mappingResults: MappingResults, line: XmlLine) {
        val newStrings = newStrings ?: throw IllegalStateException("Must call prepareLanguage(String) first")

        if (mappingResults.notFound()) {
            // Append to the strings_new.xml file
            newStrings.append("<!-- Unmapped key -->")
            newStrings.append("<!-- <string name=\"${line.key}\">${line.value}</string> -->" + System.lineSeparator())
        }
        else {
            val mapping = mappingResults.mapping!!
            val keys = mapping.keys!!
            for (key in keys) {
                // Find which file contains this key
                val writer = keyToFile[key]

                if (writer != null) {
                    // Update it
                    writer.addUpdateToKey(key, line.value, mapping.note)
                }
                else {
                    // Add to new Strings
                    newStrings.append("<!-- Key not found in a string file -->")
                    newStrings.append("<!-- <string name=\"$key\">${line.value}</string> -->" + System.lineSeparator())
                }
            }
        }
    }

    fun flush() {
        val newStrings = newStrings ?: throw IllegalStateException("Must call prepareLanguage(String) first")

        // Write the files!
        newStrings.flush()
        for ((key, value) in keyToFile) {
            value.flush()
        }
    }
}
