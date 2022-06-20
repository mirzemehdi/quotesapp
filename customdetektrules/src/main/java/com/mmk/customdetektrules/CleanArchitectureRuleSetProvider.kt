package com.mmk.customdetektrules

import com.mmk.customdetektrules.rules.DataImportsPresentationLayersRule
import com.mmk.customdetektrules.rules.DomainImportsAndroidModuleRule
import com.mmk.customdetektrules.rules.DomainImportsOtherLayersRule
import com.mmk.customdetektrules.rules.PresentationImportsDataLayersRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class CleanArchitectureRuleSetProvider : RuleSetProvider {
    override val ruleSetId = "clean-architecture-rules"
    override fun instance(config: Config) =
        RuleSet(
            ruleSetId,
            listOf(
                DomainImportsOtherLayersRule(),
                DomainImportsAndroidModuleRule(),
                PresentationImportsDataLayersRule(),
                DataImportsPresentationLayersRule()
            )
        )
}
