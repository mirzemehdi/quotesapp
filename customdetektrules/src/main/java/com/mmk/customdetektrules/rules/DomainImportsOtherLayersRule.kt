package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtImportDirective

class DomainImportsOtherLayersRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue = Issue(
        "DomainLayerShouldNotImportFromOtherLayers",
        Severity.CodeSmell,
        "Domain layer should not import from other layers. " +
                "Importing from data or presentation layers is a violation of clean architecture",
        Debt.TEN_MINS
    )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.containingFileIsInDomainLayer() &&
            importDirective.isImportedFromForbiddenLayers()
        )

            report(
                CodeSmell(
                    issue,
                    Entity.from(importDirective),
                    "Importing '${importDirective.packageName}' (not a domain layer file) in domain layer" +
                            " violates clean architecture."
                )
            )
    }

    private fun KtImportDirective.isImportedFromForbiddenLayers() =
        isDataPackage() || isPresentationPackage()

    private val KtImportDirective.packageName: String
        get() = importPath?.pathStr ?: ""

    private fun KtImportDirective.isPresentationPackage() =
        packageName.contains(".presentation.") ||
                packageName.endsWith(".presentation")

    private fun KtImportDirective.isDataPackage() =
        packageName.contains(".data.") ||
                packageName.endsWith(".data")

    private fun KtImportDirective.containingFileIsInDomainLayer() =
        containingKtFile.packageDirective?.qualifiedName?.contains(".domain.") ?: false ||
                containingKtFile.packageDirective?.qualifiedName?.endsWith(".domain") ?: false
}
