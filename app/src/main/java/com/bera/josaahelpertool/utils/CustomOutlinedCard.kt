package com.bera.josaahelpertool.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedCard(modifier: Modifier, label: String, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column {
                content()
            }
        }

        Text(
            text = label,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .offset(x = 12.dp, y = (-8).dp)
                .background(MaterialTheme.colorScheme.surface) // Adjust the padding as needed
        )
    }
}
