package com.mx.mundet.eats.ui.mvp.login

import com.mx.mundet.eats.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface LoginComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create () : LoginComponent
    }

    fun inject(activity: LoginActivity)
}