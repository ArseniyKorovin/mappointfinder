package ru.ascintegraciya.mappointfinder.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController

fun <T> Fragment.getNavigationResult(key: String): MutableLiveData<T?>? {
    val liveData = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T?>(key)
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T?>(key)
    return liveData
}

fun <T> Fragment.setNavigationResult(key: String, result: T?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}