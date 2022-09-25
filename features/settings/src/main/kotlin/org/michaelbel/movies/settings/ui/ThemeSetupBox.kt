package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.settings.ktx.themeTextRes
import org.michaelbel.movies.ui.SystemTheme

@Composable
fun ThemeSetupBox(
    modifier: Modifier = Modifier,
    themes: List<SystemTheme>,
    currentTheme: SystemTheme,
    onThemeSelected: (SystemTheme) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        themes.forEach { systemTheme: SystemTheme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onThemeSelected(systemTheme)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentTheme == systemTheme,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.secondary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp
                        )
                )

                Text(
                    text = stringResource(systemTheme.themeTextRes),
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        )
                )
            }
        }
    }
}