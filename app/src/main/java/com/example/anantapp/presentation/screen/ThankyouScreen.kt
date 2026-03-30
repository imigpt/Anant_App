package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R

@Composable
fun ThankyouScreen(
    onDownloadInvoiceClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Full-screen Background Image (Starry Space Background)
        Image(
            painter = painterResource(id = R.drawable.thankyou_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 18.dp, end = 18.dp, top = 54.dp, bottom = 24.dp)
                .clip(RoundedCornerShape(38.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.24f),
                            Color.White.copy(alpha = 0.11f),
                            Color.White.copy(alpha = 0.08f)
                        )
                    )
                )
                .border(
                    width = 3.dp,
                    color = Color.White.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(38.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.16f),
                                    Color.White.copy(alpha = 0.09f)
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.2f),
                            RoundedCornerShape(28.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Invoice Has been",
                                color = Color.White.copy(alpha = 0.45f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Sent to Your Email",
                                color = Color.White.copy(alpha = 0.75f),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(width = 114.dp, height = 44.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.78f))
                                .clickable { onDownloadInvoiceClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Download\nNow",
                                color = Color(0xFF4B4B58),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.purpal_party_popper),
                        contentDescription = "Celebration illustration",
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.White.copy(alpha = 0.22f))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(30.dp))
                        .padding(start = 28.dp, end = 28.dp, top = 30.dp, bottom = 24.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Thank You for\nYour Kindness!",
                            color = Color.White.copy(alpha = 0.94f),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 29.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Your donation will help orphan\nchildren live with care and dignity.\nYou're now part of a trusted\ncommunity that stands together in\ntimes of need.",
                            color = Color.White.copy(alpha = 0.57f),
                            fontSize = 15.5.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Skip",
                                color = Color.White.copy(alpha = 0.58f),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.clickable { onSkipClick() }
                            )

                            Box(
                                modifier = Modifier
                                    .size(width = 86.dp, height = 58.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color.White.copy(alpha = 0.9f))
                                    .clickable { onNextClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Next",
                                    color = Color(0xFF111015),
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThankyouScreenPreview() {
    ThankyouScreen()
}

