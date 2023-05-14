package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtImportDirective

class DataImportsPresentationLayersRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue = Issue(
        "DataLayerShouldNotImportFromPresentationLayer",
        Severity.CodeSmell,
        "Data layer should not import from presentation layer. " +
                "Importing from presentation layer is a violation of clean architecture. ",
        Debt.TEN_MINS
    )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.containingFileIsInDataLayer() &&
            importDirective.containingFileDoesNotContainKoinModules() &&
            importDirective.isPresentationPackage()
        )
            report(
                CodeSmell(
                    issue,
                    Entity.from(importDirective),
                    "Importing '${importDirective.packageName}' (presentation layer file) " +
                            "in data layer violates clean architecture. "
                )
            )
    }

    private val KtImportDirective.packageName: String
        get() = importPath?.pathStr ?: ""

    private fun KtImportDirective.isPresentationPackage() =
        packageName.contains(".presentation.") ||
                packageName.endsWith(".presentation")

    private fun KtImportDirective.containingFileIsInDataLayer() =
        containingKtFile.packageDirective?.qualifiedName?.contains(".data.") ?: false ||
                containingKtFile.packageDirective?.qualifiedName?.endsWith(".data") ?: false

    /**
     * If file is in di package, meaning only dependency injection can use import from data layer.
     */
    private fun KtImportDirective.containingFileDoesNotContainKoinModules(): Boolean {

        val containsKoin =
            containingKtFile.importDirectives.find {
                it.packageName.contains("koin.core.module") ||
                        it.packageName.contains("koin.dsl.module")
            }
                ?.let { true } ?: false

        return !containsKoin
    }
}
