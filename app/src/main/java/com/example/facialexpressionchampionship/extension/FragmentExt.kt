package com.example.facialexpressionchampionship.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.showFragment(fragmentManager: FragmentManager, @IdRes containerViewId: Int, isStack: Boolean) {
    if (isStack) {
        fragmentManager.beginTransaction()
            .replace(containerViewId, this)
            .addToBackStack(null)
            .commit()
    } else {
        fragmentManager.popBackStack()
        fragmentManager
            .beginTransaction()
            .replace(containerViewId, this)
            .commit()
    }
}
