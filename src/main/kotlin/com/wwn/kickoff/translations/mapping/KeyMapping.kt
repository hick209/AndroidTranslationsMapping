package com.wwn.kickoff.translations.mapping

class KeyMapping {
    var keys: Array<String>? = null
    var note: String? = null

    override fun toString(): String {
        return "[ ${keys?.joinToString { it }} ], note: $note"
    }
}