package com.mmk.common.ui.fragmentdelegations

class FragmentMainMethods : IFragmentMainMethods {
    override fun callMainMethods() {
        initView()
        setClicks()
        observeValues()
    }
}
