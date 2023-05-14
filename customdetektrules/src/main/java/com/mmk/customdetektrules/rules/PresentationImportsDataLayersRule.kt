package com.mmk.customdetektrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtImportDirective

class PresentationImportsDataLayersRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue = Issue(
        "PresentationLayerShouldNotImportFromDataLayer",
        Severity.CodeSmell,
        "Presentation layer should not import from data layer. " +
                "Importing from data layer is a violation of clean architecture. " +
                "Instead you should create UseCases in domain layer, " +
                "and use UseCases in presentation layer.",
        Debt.TEN_MINS
    )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.containingFileIsInPresentationLayer() &&
            importDirective.containingFileDoesNotContainKoinModules() &&
            importDirective.isDataPackage()
        )
            report(
                CodeSmell(
                    issue,
                    Entity.from(importDirective),
                    "Importing '${importDirective.packageName}' (data layer file) " +
                            "in presentation layer violates clean architecture. " +
                            "Instead create useCase in domain layer, and use it."
                )
            )
    }

    private val KtImportDirective.packageName: String
        get() = importPath?.pathStr ?: ""

    private fun KtImportDirective.isDataPackage() =
        packageName.contains(".data.") ||
                packageName.endsWith(".data")

    private fun KtImportDirective.containingFileIsInPresentationLayer() =
        containingKtFile.packageDirective?.qualifiedName?.contains(".presentation.") ?: false ||
                containingKtFile.packageDirective?.qualifiedName?.endsWith(".presentation") ?: false

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
