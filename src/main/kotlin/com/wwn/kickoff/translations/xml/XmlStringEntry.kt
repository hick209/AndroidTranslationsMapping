package com.wwn.kickoff.translations.xml

class XmlStringEntry(lineBreaks: Int, original: XmlLine) {

    private val lineBreaks = lineBreaks
    private val originalLine = original

    private val updates = arrayListOf<XmlStringUpdate>()

    val value: String = original.value


    fun toXmlFileString(): String {
        val builder = StringBuilder()
        // Add the line breaks
        repeat(lineBreaks, { builder.append(System.lineSeparator()) })

        // Add the updates
        for (update in updates) {
            builder.append(update.toXmlFileString())
                    .append(System.lineSeparator())
        }

        // Add the original string
        builder.append(ResourcesXmlWriter.INDENTATION)
                .append(originalLine.toXmlString())

        return builder.toString()
    }

    fun addUpdate(key: String, value: String, note: String?) {
        updates.add(XmlStringUpdate(key, value, note))
    }
}