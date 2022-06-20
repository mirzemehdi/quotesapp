package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtImportDirective

class DomainImportsAndroidModuleRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue = Issue(
        "DomainLayerShouldNotImportFromAndroidModule",
        Severity.CodeSmell,
        "Domain layer should not depend on any Android framework or library. " +
                "Importing from android framework is a violation of clean architecture",
        Debt.TEN_MINS
    )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.containingFileIsInDomainLayer() &&
            importDirective.isImportedFromAndroid()
        )
            report(
                CodeSmell(
                    issue,
                    Entity.from(importDirective),
                    "Importing '${importDirective.packageName}' (file related to Android) in domain layer" +
                            " violates clean architecture."
                )
            )
    }

    private val KtImportDirective.packageName: String
        get() = importPath?.pathStr ?: ""

    private fun KtImportDirective.isImportedFromAndroid() =
        packageName.contains("android.") || packageName.contains("androidx.")

    private fun KtImportDirective.containingFileIsInDomainLayer() =
        containingKtFile.packageDirective?.qualifiedName?.contains(".domain.") ?: false ||
                containingKtFile.packageDirective?.qualifiedName?.endsWith(".domain") ?: false
}
