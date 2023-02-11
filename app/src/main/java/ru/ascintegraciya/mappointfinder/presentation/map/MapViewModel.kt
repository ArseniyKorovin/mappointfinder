package ru.ascintegraciya.mappointfinder.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ascintegraciya.mappointfinder.data.entity.OneTimeDataWrapper
import ru.ascintegraciya.mappointfinder.data.entity.PointToMap

class MapViewModel(val pointToMap: PointToMap?, val pointToMapCode: String) : ViewModel() {

    private val mNewPointToMap = MutableStateFlow<OneTimeDataWrapper<PointToMap?>>(
        OneTimeDataWrapper(null)
    )
    val newPointToMap = mNewPointToMap.asStateFlow()

    fun setNewPointToMap(newPointToMap: PointToMap) {
        mNewPointToMap.value = OneTimeDataWrapper(newPointToMap)
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val pointToMap: PointToMap?,
        private val pointToMapCode: String
        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MapViewModel(pointToMap, pointToMapCode) as T
        }
    }
}