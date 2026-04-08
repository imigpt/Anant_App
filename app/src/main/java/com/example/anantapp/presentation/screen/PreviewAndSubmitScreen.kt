package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.anantapp.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.data.model.PreviewAndSubmitResult
import com.example.anantapp.data.model.PreviewAndSubmitState
import com.example.anantapp.presentation.viewmodel.PreviewAndSubmitViewModel

/**
 * Preview & Submit Screen
 * Manages two screens: Preview form and Success screen
 */
@Composable
fun PreviewAndSubmitScreen(
    viewModel: PreviewAndSubmitViewModel = viewModel(),
    fundraiserId: String = "FR-2025-001",
    fundraiserTitle: String = "Help Mohan fight Cancer",
    fundraiserStory: String = "Mohan needs urgent chemo...",
    goalAmount: String = "₹ 1,00,000",
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
    onSubmitSuccess: (fundraiserId: String) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value
    val result = viewModel.result.collectAsState().value
    var showSuccessScreen by remember { mutableStateOf(false) }
    var showStatusScreen by remember { mutableStateOf(false) }
    var submittedFundraiserId by remember { mutableStateOf("") }
    
    // Handle result changes
    LaunchedEffect(result) {
        when (result) {
            is PreviewAndSubmitResult.DraftSaved -> {
                onDraftSaved()
                viewModel.resetResult()
            }
            is PreviewAndSubmitResult.Success -> {
                showSuccessScreen = true
                submittedFundraiserId = result.fundraiserId
                viewModel.resetResult()
            }
            is PreviewAndSubmitResult.Error -> {
                viewModel.resetResult()
            }
            else -> {}
        }
    }
    
    if (showStatusScreen) {
        FundraiserStatusScreen(
            fundraiserId = submittedFundraiserId,
            fundraiserTitle = fundraiserTitle,
            goalAmount = goalAmount,
            onHomeClick = {
                showStatusScreen = false
                onSubmitSuccess(submittedFundraiserId)
            }
        )
    } else if (showSuccessScreen) {
        SuccessScreen(
            fundraiserId = submittedFundraiserId,
            onCheckStatusClick = {
                showSuccessScreen = false
                showStatusScreen = true
            }
        )
    } else {
        PreviewAndSubmitContent(
            fundraiserId = fundraiserId,
            onSaveDraft = { viewModel.saveDraft() },
            onSubmit = { viewModel.submitFundraiser() },
            fundraiserTitle = fundraiserTitle,
            fundraiserStory = fundraiserStory,
            goalAmount = goalAmount,
            uiState = uiState,
            onBackClick = onBackClick
        )
    }
}

@Composable
private fun PreviewAndSubmitContent(
    fundraiserId: String,
    onSaveDraft: () -> Unit,
    onSubmit: () -> Unit,
    fundraiserTitle: String,
    fundraiserStory: String,
    goalAmount: String,
    uiState: PreviewAndSubmitState,
    onBackClick: () -> Unit
) {
    // Define gradients
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF8B00FF), Color(0xFFFF3366)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val gradientBorder = Brush.linearGradient(
        colors = listOf(Color(0xFF9C27B0), Color(0xFFFF9800))
    )

    // Confirmation checkbox state
    val confirmationChecked = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ==================== Header ====================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF8B00FF),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Title
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Preview & Submit",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(40.dp))
            }

            // Fundraiser ID Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Fundraiser ID",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = fundraiserId,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }

            // ==================== Main Content Card ====================
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    // ========== Fundraiser Info Box ==========
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Avatar
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFFCCCCCC),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.person_icon),
                                    contentDescription = "Avatar",
                                    modifier = Modifier.size(32.dp),
                                    contentScale = ContentScale.Fit,
                                    colorFilter = ColorFilter.tint(Color.Black)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            // Text content
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Title",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF999999)
                                )
                                Text(
                                    text = fundraiserTitle,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF333333)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Story",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF999999)
                                )
                                Text(
                                    text = fundraiserStory,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF666666),
                                    maxLines = 2
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Goal Amount
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total Goal Amount",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFF666666)
                                    )
                                    Text(
                                        text = goalAmount,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2196F3)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ========== Beneficiary Details ==========
                    Text(
                        text = "Beneficiary Details",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    VerificationField(
                        text = "Verified",
                        borderBrush = gradientBorder
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ========== Documents Upload ==========
                    Text(
                        text = "Documents Upload",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    VerificationField(
                        text = "Verified",
                        borderBrush = gradientBorder
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // ========== Confirmation Checkbox ==========
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Checkbox
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (confirmationChecked.value) Color(0xFF8B00FF) else Color.Transparent
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (confirmationChecked.value) Color(0xFF8B00FF) else Color(0xFFCCCCCC),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { confirmationChecked.value = !confirmationChecked.value },
                            contentAlignment = Alignment.Center
                        ) {
                            if (confirmationChecked.value) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Confirmed",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "I confirm all details are true and documents are valid.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF999999)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ========== Submit Text ==========
                    Text(
                        text = "Submit for Admin Approval",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            // ==================== Bottom Action Buttons ====================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Draft Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .clickable(enabled = !uiState.isLoading) { onSaveDraft() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Draft",
                            tint = Color(0xFF8B00FF),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Draft",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333)
                        )
                    }
                }

                // Submit Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .clickable(enabled = !uiState.isLoading) { onSubmit() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.isLoading) "Submitting..." else "Submit",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )
                }
            }
        }
    }
}

/**
 * Success Screen Component
 * Displays celebratory screen after successful fundraiser submission
 */
@Composable
private fun SuccessScreen(
    fundraiserId: String,
    onCheckStatusClick: () -> Unit
) {
    // Define gradients
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF8B00FF), Color(0xFFFF3366)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
    ) {
        // Confetti background
        ConfettiBackground()

        // Party popper image
        Image(
            painter = painterResource(id = R.drawable.party_popper),
            contentDescription = "Party Popper Celebration",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = 24.dp, start = 8.dp),
            alignment = Alignment.TopStart,
            contentScale = ContentScale.Fit
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // White card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Green checkmark in circle
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .background(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(44.dp)
                            )
                            .border(
                                width = 3.dp,
                                color = Color(0xFF4CAF50),
                                shape = RoundedCornerShape(44.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Heading
                    Text(
                        text = "Fundraiser Submitted\nSuccessfully!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    Text(
                        text = "Thank you for sharing your details and documents. Your fundraiser is now under admin review for verification.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Check Status Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clickable { onCheckStatusClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Check Status",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/**
 * Fundraiser Status Screen Component
 * Displays the status of the submitted fundraiser with timeline
 */
@Composable
private fun FundraiserStatusScreen(
    fundraiserId: String,
    fundraiserTitle: String,
    goalAmount: String,
    onHomeClick: () -> Unit
) {
    // Define gradients
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF8B00FF), Color(0xFFFF3366)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val statusGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9C27B0), Color(0xFFFF3366))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ==================== Header ====================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(64.dp),
//
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.person_icon),
                        contentDescription = "Status",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                Text(
                    text = "Fundraiser Status",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Subtitle
                Text(
                    text = "Your fundraiser is under review",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Fundraiser ID Display
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Fundraiser ID",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = fundraiserId,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==================== Campaign Details Card ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(56.dp),
//
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.person_icon),
                            contentDescription = "Avatar",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                    }

                    // Details
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Title",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF999999)
                        )
                        Text(
                            text = fundraiserTitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Target Amount",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF999999)
                        )
                        Text(
                            text = goalAmount,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2196F3)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Submitted on: 01 July 2025",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF999999)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==================== Status Timeline Card ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Timeline Item 1 - Documents Submitted (Completed)
                    StatusTimelineItem(
                        title = "Documents Submitted",
                        icon = Icons.Default.Check,
                        status = "Done",
                        gradient = statusGradient
                    )

                    // Timeline Item 2 - Admin Reviewing (In Progress)
                    StatusTimelineItem(
                        title = "Admin Reviewing Docs",
                        icon = Icons.Default.AccessTime,
                        status = "In Progress",
                        gradient = statusGradient
                    )

                    // Timeline Item 3 - Final Approval (Pending)
                    StatusTimelineItem(
                        title = "Final Approval & Go Live",
                        icon = Icons.Default.HourglassBottom,
                        status = "Pending",
                        gradient = statusGradient
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Estimated time text
                    Text(
                        text = "Estimated time: 24-48 hrs for full verification.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ==================== Home Button ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clickable { onHomeClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Home",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Status Timeline Item Component
 * Individual status item in the timeline
 */
@Composable
private fun StatusTimelineItem(
    title: String,
    icon: ImageVector,
    status: String,
    gradient: Brush
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = gradient, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon and Title
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Dot Icon
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color(0xFF8B00FF),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Title
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            // Status Badge
            Box(
                modifier = Modifier
                    .background(
                        color = when (status) {
                            "Done" -> Color(0xFFE8F5E9)
                            "In Progress" -> Color(0xFFFFF3E0)
                            else -> Color(0xFFF3E5F5)
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = status,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (status) {
                        "Done" -> Color(0xFF4CAF50)
                        "In Progress" -> Color(0xFFFFA500)
                        else -> Color(0xFF9C27B0)
                    }
                )
            }
        }
    }
}

/**
 * Confetti Background Component
 * Renders colorful celebratory confetti pieces
 */
@Composable
private fun ConfettiBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        repeat(30) { index ->
            val randomColor = listOf(
                Color(0xFFFF6B6B),
                Color(0xFF4ECDC4),
                Color(0xFFFFE66D),
                Color(0xFF95E1D3),
                Color(0xFFF38181),
                Color(0xFFAA96DA),
                Color(0xFFFCBB42)
            )[index % 7]

            val xPosition = (index * 37) % 100
            val yPosition = (index * 13) % 100
            val size = (8 + (index % 6)).dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .offset(x = (xPosition.toFloat() / 100 * 300).dp, y = (yPosition.toFloat() / 100 * 600).dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(randomColor, RoundedCornerShape(2.dp))
                )
            }
        }
    }
}

/**
 * Verification Field Component
 * Displays a field with gradient border, rounded corners, and status
 */
@Composable
private fun VerificationField(
    text: String,
    borderBrush: Brush,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333)
            )

            // Green checkmark icon
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color(0xFFC8E6C9),
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAndSubmitScreenPreview() {
    PreviewAndSubmitContent(
        fundraiserId = "FR-2025-001",
        onSaveDraft = {},
        onSubmit = {},
        fundraiserTitle = "Help Mohan fight Cancer",
        fundraiserStory = "Mohan needs urgent chemo therapy. He is a father of two...",
        goalAmount = "₹ 1,00,000",
        uiState = PreviewAndSubmitState(isLoading = false),
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    SuccessScreen(fundraiserId = "12345", onCheckStatusClick = {})
}

@Preview(showBackground = true)
@Composable
fun FundraiserStatusScreenPreview() {
    FundraiserStatusScreen(
        fundraiserId = "12345",
        fundraiserTitle = "Help Mohan Fight Cancer",
        goalAmount = "₹ 1,00,000",
        onHomeClick = {}
    )
}
