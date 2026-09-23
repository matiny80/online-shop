package ir.matinyakhshi.onlineshop.domain.model

import androidx.annotation.DrawableRes

data class Category(
    val id: String,
    val title: String,
    val imageUrl: String = "",
    @DrawableRes val iconRes: Int? = null
)