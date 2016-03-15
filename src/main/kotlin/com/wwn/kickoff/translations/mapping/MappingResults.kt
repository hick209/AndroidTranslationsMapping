package com.wwn.kickoff.translations.mapping

data class MappingResults(val mapping: KeyMapping? = null,
                          val unused: Boolean = false) {

    fun notFound(): Boolean = (!unused && mapping == null)
}