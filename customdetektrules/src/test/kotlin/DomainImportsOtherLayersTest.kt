package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DomainImportsOtherLayersTest {

    private fun getWarningMessage(importedFile: String): String {
        val warningMessageFormat = "Importing '%s' (not a domain layer file) in domain layer violates clean architecture."
        return warningMessageFormat.format(importedFile)
    }

    @Test
    fun importingFileFromDataLayerInDomainLayer_givesWarning() {
        val importedFile = "com.data.DataFile"
        val findings = DomainImportsOtherLayersRule().lint(
            """  
                 package com.domain
                
                 import $importedFile
            """.trimIndent()
        )

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message).isEqualTo(getWarningMessage(importedFile))
    }

    @Test
    fun importingFileFromPresentationLayerInDomainLayer_givesWarning() {
        val importedFile = "com.presentation.PresentationFile"
        val findings = DomainImportsOtherLayersRule().lint(
            """  
                 package com.domain
                
                 import $importedFile
            """.trimIndent()
        )

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message).isEqualTo(getWarningMessage(importedFile))
    }

    @Test
    fun importingFileFromDomainLayerInDomainLayer_DoesNotGiveWarning() {
        val importedFile = "com.domain.DomainFile"
        val findings = DomainImportsOtherLayersRule().lint(
            """  
                 package com.domain
                
                 import $importedFile
            """.trimIndent()
        )

        assertThat(findings).hasSize(0)
    }
}
