package com.fortheworthy.game.fairgoslots.viewPart.compose

import android.graphics.Typeface
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.fortheworthy.game.fairgoslots.R

@Composable
fun TextWithGoldBorder(@StringRes stringId: Int, shouldBeScrollable: Boolean = true) {
    TextWithGoldBorder(stringResource(id = stringId), shouldBeScrollable)
}

@Composable
fun TextWithGoldBorder(string: String, shouldBeScrollable: Boolean = true) {
    val scrollState = rememberScrollState(0)
    Text(
        text = string,
        color = Color.White,
        fontFamily = FontFamily(
            ResourcesCompat.getFont(
                LocalContext.current,
                R.font.montserrat_medium
            )
                ?: Typeface.DEFAULT
        ),
        lineHeight = TextUnit(20f, TextUnitType.Sp),
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.privacy_policy_text_bg),
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp, Brush.horizontalGradient(
                    listOf(
                        Color(0xFFBF9000),
                        Color(0xFFFFFF00),
                        Color(0xFFFBBD00),
                        Color(0xFFFFFF00),
                    ),
                ),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .let {
                if (shouldBeScrollable) {
                    it.verticalScroll(scrollState)
                }
                else {
                    it
                }
            }
    )
}

@Preview
@Composable
fun TempPreview() {
    TextWithGoldBorder(R.string.privacy_policy_text)
}