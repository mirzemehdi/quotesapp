package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions
import org.junit.Test

class DataImportsPresentationLayersRuleTest {

    private fun getWarningMessage(importedFile: String): String {
        val warningMessageFormat = "Importing '%s' (presentation layer file) " +
                "in data layer violates clean architecture. "
        return warningMessageFormat.format(importedFile)
    }

    @Test
    fun importingFileFromPresentationLayerInDataLayer_givesWarning() {
        val importedFile = "com.presentation.PresentationLayerFile"
        val findings = DataImportsPresentationLayersRule().lint(
            """  
                 package com.data
                
                 import $importedFile
            """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(1)
        Assertions.assertThat(findings[0].message).isEqualTo(getWarningMessage(importedFile))
    }

    @Test
    fun importingFileFromPresentationLayerInOtherLayerContainsData_givesNoWarning() {
        val importedFile = "com.presentation.PresentationLayerFile"
        val findings = DataImportsPresentationLayersRule().lint(
            """  
                 package com.somedata
                
                 import $importedFile
            """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(0)
    }

    @Test
    fun importingFileFromOtherLayerContainsPresentationInDataLayer_givesNoWarning() {
        val importedFile = "com.somepresentation"
        val findings = DataImportsPresentationLayersRule().lint(
            """  
                 package com.data
                
                 import $importedFile
            """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(0)
    }

    @Test
    fun importingFileFromDataLayerInPresentationLayerInDIPackage_givesNoWarning() {
        val importedFile = "com.presentation.PresentationLayerFile"
        val findings = DataImportsPresentationLayersRule().lint(
            """  
                 package com.data
                
                 import $importedFile
                 import org.koin.dsl.module
            """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(0)
    }
}
