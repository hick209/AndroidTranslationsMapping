package com.wwn.kickoff.translations.onesky

import com.wwn.kickoff.translations.xml.ResourcesXmlProcessor
import com.wwn.kickoff.translations.xml.XmlLine
import java.io.File
import java.nio.file.Files

/**
 * @author Nivaldo Bondança
 */
class NewTranslations(oneSkyTranslationsDir: File, languages: Array<String>) {

    private val translations: MutableMap<String, File> = hashMapOf()

    init {
        for (language in languages) {
            val values = File(oneSkyTranslationsDir, language)
            val stringsFiles = values.listFiles()
            for (file in stringsFiles) {
                translations.put(language, file)
            }
        }
    }

    fun process(language: String): List<XmlLine> {
        val xmlFile = translations[language]!!
        return ResourcesXmlProcessor(xmlFile).read()
    }
}
