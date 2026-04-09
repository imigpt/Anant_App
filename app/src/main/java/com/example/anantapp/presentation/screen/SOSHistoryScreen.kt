package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SOSHistoryRecord(
    val id: String,
    val date: String,
    val time: String,
    val location: String,
    val alertedContacts: Int,
    val status: String
)

@Composable
fun SOSHistoryScreen(
    onBackClick: () -> Unit = {},
    onViewMapClick: (String) -> Unit = {},
    onDownloadPDFClick: (String) -> Unit = {}
) {
    val sosRecords = listOf(
        SOSHistoryRecord(
            id = "sos_001",
            date = "3 July 2025",
            time = "10:15 PM",
            location = "Ring Road, Delhi",
            alertedContacts = 2,
            status = "Resolved"
        ),
        SOSHistoryRecord(
            id = "sos_002",
            date = "4 July 2025",
            time = "10:15 PM",
            location = "Ring Road, Delhi",
            alertedContacts = 2,
            status = "Resolved"
        )
    )

    // Full screen background gradient
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF3333), // Red
                        Color(0xFFFF7A00)  // Orange
                    )
                )
            )
    ) {
        // Floating white container card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                .shadow(elevation = 16.dp, shape = RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 32.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Custom Logo (Life Preserver / Crosshair)
            Canvas(modifier = Modifier.size(72.dp)) {
                val strokeWidth = 0.3f
                val radius = (size.minDimension - strokeWidth) / 2
                val center = Offset(size.width / 2, size.height / 2)

                // Draw 4 rounded arc segments
                for (i in 0 until 4) {
                    drawArc(
                        color = Color.Black,
                        startAngle = (i * 90f) + 15f, // Offset to create gaps
                        sweepAngle = 60f,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = "SOS History",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "View your past SOS alerts for safety records.",
                fontSize = 14.sp,
                color = Color(0xFF4B5563), // Gray
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // History List
            sosRecords.forEach { record ->
                SOSHistoryCard(
                    record = record,
                    onViewMapClick = { onViewMapClick(record.id) },
                    onDownloadPDFClick = { onDownloadPDFClick(record.id) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SOSHistoryCard(
    record: SOSHistoryRecord,
    onViewMapClick: () -> Unit = {},
    onDownloadPDFClick: () -> Unit = {}
) {
    // Card with horizontal red-to-orange gradient
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF3B30), // Red
                        Color(0xFFFF9500)  // Orange
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Data Rows
            InfoRow(icon = Icons.Default.DateRange, text = "Date - ${record.date}, ${record.time}")
            InfoRow(icon = Icons.Default.LocationOn, text = "Location: ${record.location}")
            InfoRow(icon = Icons.Default.Notifications, text = "Alerted: Family (${record.alertedContacts})")
            InfoRow(icon = Icons.Default.CheckCircle, text = "Status: ${record.status}", isLast = true)

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onViewMapClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(50),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        text = "View on Map",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Button(
                    onClick = onDownloadPDFClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(50),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Download PDF",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String, isLast: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isLast) 20.dp else 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SOSHistoryScreenPreview() {
    SOSHistoryScreen()
}
