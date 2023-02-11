package ru.ascintegraciya.mappointfinder.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointToMap(
    val latitude: Double,
    val longitude: Double,
    val address: String,
) : Parcelable
