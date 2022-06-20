package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.assertj.core.api.Assertions
import org.junit.Test

class DomainImportsAndroidModuleRuleTest {

    private fun getWarningMessage(importedFile: String): String {
        val warningMessageFormat = "Importing '%s' (file related to Android) in domain layer violates clean architecture."
        return warningMessageFormat.format(importedFile)
    }

    @Test
    fun importingFileFromAndroidModuleInDomainLayer_givesWarning() {
        val importedFile = "android.AndroidFile"
        val findings = DomainImportsAndroidModuleRule().lint(
                """  
                     package com.domain
                    
                     import $importedFile
                """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(1)
        Assertions.assertThat(findings[0].message).isEqualTo(getWarningMessage(importedFile))
    }

    @Test
    fun importingFileFromAndroidXModuleInDomainLayer_givesWarning() {
        val importedFile = "androidx.AndroidFile"
        val findings = DomainImportsAndroidModuleRule().lint(
                """  
                     package com.domain
                    
                     import $importedFile
                """.trimIndent()
        )

        Assertions.assertThat(findings).hasSize(1)
        Assertions.assertThat(findings[0].message).isEqualTo(getWarningMessage(importedFile))
    }
}
