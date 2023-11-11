package com.fortheworthy.game.fairgoslots.modelPart

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Article(
    @StringRes val titleId: Int,
    @StringRes val textId: Int,
    @DrawableRes val imageId: Int
)
