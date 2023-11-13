package com.bera.josaahelpertool.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bera.josaahelpertool.ui.theme.md_theme_light_onSurface
import com.bera.josaahelpertool.ui.theme.rubikFamily

@Composable
fun CustomDivider(modifier: Modifier = Modifier, text: String, alignment: Alignment = Alignment.Center) {
    Box(modifier = modifier) {
        Divider(modifier = Modifier.align(Alignment.Center))
        Text(
            modifier = Modifier
                .align(alignment)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 4.dp),
            text = text,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}