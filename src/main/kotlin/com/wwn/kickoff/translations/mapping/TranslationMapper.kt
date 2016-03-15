package com.wwn.kickoff.translations.mapping

import com.google.gson.Gson
import java.io.File
import java.nio.file.Files

/**
 * @author Nivaldo Bondança
 */
class TranslationMapper(mappingFile: File) {

    private val mapping: Mapping

    init {
        val content = String(Files.readAllBytes(mappingFile.toPath()))
        mapping = Gson().fromJson(content, Mapping::class.java)
    }

    fun map(key: String): MappingResults {
        val keyMapping = mapping.keyMap?.get(key)
        if (keyMapping != null) {
            return MappingResults(keyMapping)
        }

        if (mapping.unused!!.contains(key) || mapping.mightUse!!.contains(key)) {
            return MappingResults(unused = true)
        }

        return MappingResults()
    }

    override fun toString(): String {
        return mapping.toString()
    }
}

