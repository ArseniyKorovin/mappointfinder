package ru.ascintegraciya.mappointfinder.presentation.selectedpoint

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ascintegraciya.mappointfinder.data.entity.OneTimeDataWrapper
import ru.ascintegraciya.mappointfinder.data.entity.PointToMap

class SelectedPointToMapViewModel : ViewModel() {

    private val mPointToMap = MutableStateFlow<OneTimeDataWrapper<PointToMap?>>(
        OneTimeDataWrapper(null)
    )
    val pointToMap = mPointToMap.asStateFlow()

    fun setPointToMap(pointToMap: PointToMap) {
        mPointToMap.value = OneTimeDataWrapper(pointToMap)
    }
}