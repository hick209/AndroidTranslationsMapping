package com.wwn.kickoff.translations.xml

import java.io.File

/**
 * @author Nivaldo Bondança
 */
class ResourcesXmlProcessor(xmlFile: File) {

    companion object {
        val XML_HEADER      = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        val RESOURCES_OPEN  = "<resources>"
        val RESOURCES_CLOSE = "</resources>"
    }

    private val fileName = xmlFile.name
    private val xmlLines = xmlFile.readLines()

    private val stringValueRegex    = Regex("<string name=\"(.+)\">(.*)<\\/string>")


    fun read(): List<XmlLine> {
        println("Reading file '$fileName'")

        if (xmlLines.isEmpty()) {
            System.err?.println("Empty file $fileName!")
            return emptyList()
        }

        var currentLine = 0

        if (XML_HEADER.equals(xmlLines[currentLine], ignoreCase = true)) {
            currentLine++
        }
        else {
            System.err?.println("File $fileName doesn't start with $XML_HEADER!")
        }

        var insideResources = false
        var insideComment = false
        val results: MutableList<XmlLine> = arrayListOf()

        while (currentLine < xmlLines.size) {
            val line = xmlLines[currentLine++].trim()
            if (line.isEmpty()) {
//                println("Line $currentLine is empty")
                continue
            }
            if (insideComment || line.startsWith("<!--")) {
//                println("Line $currentLine is a comment")
                insideComment = !line.endsWith("-->")
                continue
            }

            if (RESOURCES_OPEN.equals(line)) {
                insideResources = true
//                println("Opening resources @ line $currentLine")
                continue
            }
            else if (RESOURCES_CLOSE.equals(line)) {
                insideResources = false
//                println("Closing resources @ line $currentLine")
                continue
            }

            if (insideResources) {
                val matchResult = stringValueRegex.find(line)
                if (matchResult != null) {
                    var value = matchResult.groupValues[2]
                    // Replace all the %@ for %s
                    var i = 1
                    var index = value.indexOf("%@")
                    while (index >= 0) {
                        value = value.replaceFirst("%@", "%$i\$s")
                        i++

                        index = value.indexOf("%@")
                    }

                    // Update special characters
                    value = value
                            .replace("--", "—")
                            .replace("...", "…")

                    val xmlLine = XmlLine(currentLine - 1, matchResult.groupValues[1], value)
                    results.add(xmlLine)
                }
                else {
                    System.err?.println("Mismatch on line $currentLine! Line: $line")
                }
            }
        }

        return results
    }
}
