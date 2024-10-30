package com.example.spaceflightnews.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Launch(
    val launch_id: String,
    val provider: String
) : Parcelable
