package com.sf.tadami.navigation

import androidx.compose.animation.core.MutableTransitionState
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {
    val bottomNavDisplayed = MutableTransitionState(true)
}