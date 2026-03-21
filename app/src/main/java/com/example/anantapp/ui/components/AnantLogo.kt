package com.example.anantapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R
import com.example.anantapp.ui.theme.PrimaryRed

/**
 * Anant Logo Component
 * Displays the app icon with Anant text
 */
@Composable
fun AnantLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_anant),
            contentDescription = "Anant Logo",
            modifier = Modifier.size(56.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Anant",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryRed,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    }
}
