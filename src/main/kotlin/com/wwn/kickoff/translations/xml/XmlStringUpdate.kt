package com.wwn.kickoff.translations.xml

data class XmlStringUpdate(val key: String, val newValue: String, val note: String?) {
    fun toXmlFileString(): String {
        val extra: String
        extra = if (note != null) {
            "Note: $note"
        }
        else {
            ""
        }

        return ResourcesXmlWriter.INDENTATION +  "<!-- Update! $extra" + System.lineSeparator() +
                ResourcesXmlWriter.INDENTATION + "<string name=\"$key\">$newValue</string>" + System.lineSeparator() +
                ResourcesXmlWriter.INDENTATION + " -->"
    }
}