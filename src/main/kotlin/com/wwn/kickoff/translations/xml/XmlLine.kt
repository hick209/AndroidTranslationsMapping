package com.wwn.kickoff.translations.xml

data class XmlLine(val line:  Int,
                   val key:   String,
                   val value: String) {

    override fun toString(): String {
        return "$line: $key = $value"
    }

    fun toXmlString(): String {
        return "<string name=\"$key\">$value</string>"
    }
}