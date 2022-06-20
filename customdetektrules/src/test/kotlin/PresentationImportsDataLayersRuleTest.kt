package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions
import org.junit.Test

class PresentationImportsDataLayersRuleTest {

    private fun getWarningMessage(importedFile: String): String {
        val warningMessageFormat = "Importing '%s' (data layer file) " +
                "in presentation layer violates clean architecture. " +
                "Instead create useCase in domain layer, and use it."
        return warningMessageFormat.format(importedFile)
    }

    @Test
    fun importingFileFromDataLayerInPresentationLayer_givesWarning() {
        val importedFile = "com.data.DataLayerFile"
        val findings = PresentationImportsDataLayersRule().lint(
            """  
                 package com.presentation
                
                 import $importedFile
            """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(1)
        Assertions.assertThat(findings[0].message).isEqualTo(getWarningMessage(importedFile))
    }

    @Test
    fun importingFileFromDataLayerInPresentationLayerInDIPackage_givesNoWarning() {
        val importedFile = "com.data.DataLayerFile"
        val findings = PresentationImportsDataLayersRule().lint(
            """  
                 package com.presentation
                
                 import $importedFile
                 import org.koin.core.module.Module
            """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(0)
    }
}
