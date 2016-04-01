package com.wwn.kickoff.translations.xml

import java.io.File

/**
 * @author Nivaldo Bondança
 */
class ResourcesXmlWriter(xmlFile: File, xmlLines: List<XmlLine>) {

    companion object {
        val INDENTATION = "    "
    }

    private val targetFile = xmlFile
    private val tempFile = createTempFile(prefix = "tmp_${targetFile.name}", directory = targetFile.parentFile)

    private val originals = linkedMapOf<String, XmlStringEntry>()
    private val lines = arrayListOf<String>()

    init {
        if (xmlLines.isNotEmpty()) {
            var lineBreaks: Int
            var lastLine = xmlLines.first().line - 1

            for (xmlLine in xmlLines) {
                lineBreaks = xmlLine.line - lastLine - 1
                lastLine = xmlLine.line

                originals.put(xmlLine.key, XmlStringEntry(lineBreaks, xmlLine))
            }
        }
    }

    fun append(line: String) {
        lines.add(line)
    }

    fun addUpdateToKey(key: String, value: String, note: String?) {
        val entry = originals[key]!!
        val originalValue = entry.value
                // Remove bold tags
                .replace(Regex("(\\<b\\>)|(\\<\\/b\\>)"), "")

        if (originalValue != value) {
            entry.addUpdate(key, value, note)
        }
    }

    fun flush() {
        if (originals.isEmpty() && lines.isEmpty()) {
            tempFile.delete()
            return
        }

        tempFile.bufferedWriter().use { out ->
            // Add the reader
            out.write(ResourcesXmlProcessor.XML_HEADER)
            out.newLine()

            // Open the resources tag
            out.write(ResourcesXmlProcessor.RESOURCES_OPEN)
            out.newLine()
            out.newLine()

            // Write the real lines + updates
            originals.forEach { key, value ->
                out.write(value.toXmlFileString())
                out.newLine()
            }

            // Write the appends
            lines.forEach {
                out.write(INDENTATION + it)
                out.newLine()
            }

            // Close the resources tag
            out.newLine()
            out.write(ResourcesXmlProcessor.RESOURCES_CLOSE)
            out.newLine()
        }

        // DONE!
        tempFile.copyTo(targetFile, true)
        tempFile.delete()
    }
}
