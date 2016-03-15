package com.wwn.kickoff.translations

import com.wwn.kickoff.translations.android.ProjectStrings
import com.wwn.kickoff.translations.mapping.TranslationMapper
import com.wwn.kickoff.translations.onesky.NewTranslations
import java.io.File

/**
 * @author Nivaldo Bondança
 */
class Translator(languages: Array<String>, androidResourcesDir: File, oneSkyTranslationsDir: File, mappingFile: File) {

    private val languages = languages

    private val project = ProjectStrings(androidResourcesDir)
    private val newTranslationsDir = NewTranslations(oneSkyTranslationsDir, languages)
    private val mapper = TranslationMapper(mappingFile)

    fun run() {
        for (language in languages) {
            project.prepareLanguage(language)

            val xmlValues = newTranslationsDir.process(language)

            for (line in xmlValues) {
                val mappingResults = mapper.map(line.key)
                if (mappingResults.unused) {
//                    println("Unused ${line.key}")
                } else {
                    project.process(mappingResults, line)
                }
            }

            project.flush()
        }
    }

}
