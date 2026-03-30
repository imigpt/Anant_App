package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.data.model.TargetAndPaymentsResult
import com.example.anantapp.data.model.TargetAndPaymentsState
import com.example.anantapp.presentation.viewmodel.TargetAndPaymentsViewModel
import com.example.anantapp.ui.components.CustomOutlinedInput
import com.example.anantapp.ui.components.FormLabel
import com.example.anantapp.ui.components.ScreenHeader

/**
 * Target & Payments Screen with Fundraiser Visibility Step
 * Two-step flow:
 * Step 1: Set Target Amount & Payment Details
 * Step 2: Configure Fundraiser Visibility Settings
 */
@Composable
fun TargetAndPaymentsScreen(
    viewModel: TargetAndPaymentsViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
    onFundraiserPublished: (fundraiserId: String) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value
    val result = viewModel.result.collectAsState().value
    
    // Step navigation: 1 = Target & Payments, 2 = Fundraiser Visibility
    var currentStep by remember { mutableIntStateOf(1) }
    
    // Visibility screen states
    var isPublic by remember { mutableStateOf(true) }
    var isPrivate by remember { mutableStateOf(false) }
    var displayDonorNames by remember { mutableStateOf(true) }
    var enableCommenting by remember { mutableStateOf(true) }
    
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264))
    )
    
    // Handle result changes
    LaunchedEffect(result) {
        when (result) {
            is TargetAndPaymentsResult.DraftSaved -> {
                onDraftSaved()
                viewModel.resetResult()
            }
            is TargetAndPaymentsResult.Success -> {
                onFundraiserPublished(result.fundraiserId)
                viewModel.resetResult()
            }
            is TargetAndPaymentsResult.Error -> {
                viewModel.resetResult()
            }
            else -> {}
        }
    }
    
    when (currentStep) {
        1 -> {
            // Step 1: Target & Payments Screen
            TargetAndPaymentsContent(
                viewModel = viewModel,
                uiState = uiState,
                mainGradient = mainGradient,
                onBackClick = onBackClick,
                onDraftClick = { viewModel.saveDraft() },
                onNextClick = { currentStep = 2 }
            )
        }
        2 -> {
            // Step 2: Fundraiser Visibility Screen
            FundraiserVisibilityContent(
                isPublic = isPublic,
                isPrivate = isPrivate,
                displayDonorNames = displayDonorNames,
                enableCommenting = enableCommenting,
                onPublicToggle = { isPublic = !isPublic },
                onPrivateToggle = { isPrivate = !isPrivate },
                onDisplayNamesToggle = { displayDonorNames = !displayDonorNames },
                onCommentingToggle = { enableCommenting = !enableCommenting },
                mainGradient = mainGradient,
                onBackClick = { currentStep = 1 },
                onDraftClick = { viewModel.saveDraft() },
                onNextClick = { viewModel.publishFundraiser() }
            )
        }
    }
}

@Composable
private fun TargetAndPaymentsContent(
    viewModel: TargetAndPaymentsViewModel,
    uiState: TargetAndPaymentsState,
    mainGradient: Brush,
    onBackClick: () -> Unit,
    onDraftClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ==================== Header ====================
            ScreenHeader(
                title = "Set Target Amount",
                onBackClick = onBackClick
            )
            
            // ==================== Scrollable Content ====================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                // ==================== Total Goal Amount Section ====================
                FormLabel("Total Goal Amount")
                Spacer(modifier = Modifier.height(8.dp))
                TotalGoalAmountInput(
                    goalAmount = uiState.goalAmount,
                    onGoalAmountChange = { viewModel.updateGoalAmount(it) }
                )
                
                // Helper Text
                Text(
                    text = "The minimum donation required is INR 25,000",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // ==================== Auto-topup Prominent Section ====================
                AutoTopupBox(
                    isAutoTopupEnabled = uiState.isAutoTopupEnabled,
                    onToggle = { viewModel.toggleAutoTopup() }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // ==================== Bank UPI ID / Wallet Link Section ====================
                FormLabel("Bank UPI ID / Wallet Link")
                Spacer(modifier = Modifier.height(8.dp))
                
                // UPI ID Input
                CustomOutlinedInput(
                    value = uiState.bankUpiId,
                    onValueChange = { viewModel.updateBankUpiId(it) },
                    placeholder = "UPI ID"
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Wallet Link Input
                CustomOutlinedInput(
                    value = uiState.walletLink,
                    onValueChange = { viewModel.updateWalletLink(it) },
                    placeholder = "Wallet Link"
                )
                
                Spacer(modifier = Modifier.height(80.dp)) // Space for sticky footer
            }
            
            // ==================== Sticky Bottom Buttons ====================
            BottomTargetPaymentButtons(
                mainGradient = mainGradient,
                onDraftClick = onDraftClick,
                onNextClick = onNextClick,
                isLoading = uiState.isLoading
            )
        }
    }
}

/**
 * Total Goal Amount Input with Currency Dropdown
 */
@Composable
private fun TotalGoalAmountInput(
    goalAmount: String,
    onGoalAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCurrencyDropdown by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf("INR") }
    val currencies = listOf("INR", "USD", "EUR", "GBP")
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Currency Dropdown
        Box(
            modifier = Modifier
                .width(70.dp)
                .height(40.dp)
                .clickable { showCurrencyDropdown = !showCurrencyDropdown },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedCurrency,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
            }
            
            DropdownMenu(
                expanded = showCurrencyDropdown,
                onDismissRequest = { showCurrencyDropdown = false },
                modifier = Modifier.width(100.dp)
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency, fontSize = 12.sp) },
                        onClick = {
                            selectedCurrency = currency
                            showCurrencyDropdown = false
                        }
                    )
                }
            }
        }
        
        // Divider
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.LightGray)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Amount Input
        BasicTextField(
            value = goalAmount,
            onValueChange = onGoalAmountChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (goalAmount.isEmpty()) {
                    Text(
                        text = "Enter Amount",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal
                    )
                }
                innerTextField()
            }
        )
    }
}

/**
 * Prominent Blue Auto-topup Container
 */
@Composable
private fun AutoTopupBox(
    isAutoTopupEnabled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color(0xFF0037FF),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onToggle() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Auto-topup",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            
            // Toggle Switch
            Box(
                modifier = Modifier
                    .size(48.dp, 28.dp)
                    .background(
                        color = if (isAutoTopupEnabled) Color.White else Color(0xFFBDBDBD),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(2.dp),
                contentAlignment = if (isAutoTopupEnabled) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = if (isAutoTopupEnabled) Color(0xFF1E88E5) else Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                )
            }
        }
    }
}

/**
 * Bottom Action Buttons for Target & Payments (Matching CreateFundraiserScreen Design)
 */
@Composable
private fun BottomTargetPaymentButtons(
    mainGradient: Brush,
    onDraftClick: () -> Unit,
    onNextClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Draft Button (Gradient Border - Outlined)
        Box(modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .border(1.5.dp, mainGradient, CircleShape)
                .clip(CircleShape)
                .clickable(enabled = !isLoading) { onDraftClick() }, contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Draft",
                    tint = Color(0xFF8B00FF),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Draft",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    fontSize = 16.sp
                )
            }
        }
        
        // Next Button (Gradient Fill)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .background(mainGradient, CircleShape)
                .clip(CircleShape)
                .clickable(enabled = !isLoading) { onNextClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isLoading) "Loading..." else "Next",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Fundraiser Visibility Screen Content
 * Configure public/private visibility and commenting settings
 */
@Composable
private fun FundraiserVisibilityContent(
    isPublic: Boolean,
    isPrivate: Boolean,
    displayDonorNames: Boolean,
    enableCommenting: Boolean,
    onPublicToggle: () -> Unit,
    onPrivateToggle: () -> Unit,
    onDisplayNamesToggle: () -> Unit,
    onCommentingToggle: () -> Unit,
    mainGradient: Brush,
    onBackClick: () -> Unit,
    onDraftClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            ScreenHeader(
                title = "Fundraiser Visibility",
                onBackClick = onBackClick
            )
            
            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Public Card
                VisibilityToggleCard(
                    title = "Public",
                    subtitle = "Anyone can see & donate",
                    isToggled = isPublic,
                    onToggle = onPublicToggle
                )
                
                // Private Card
                VisibilityToggleCard(
                    title = "Private",
                    subtitle = "Only shared link can donate",
                    isToggled = isPrivate,
                    onToggle = onPrivateToggle
                )
                
                // Display Donor Names Card
                VisibilityToggleCard(
                    title = "Display Donor Names?",
                    subtitle = null,
                    isToggled = displayDonorNames,
                    onToggle = onDisplayNamesToggle
                )
                
                // Enable Commenting Card
                VisibilityToggleCard(
                    title = "Enable Commenting?",
                    subtitle = null,
                    isToggled = enableCommenting,
                    onToggle = onCommentingToggle
                )
                
                Spacer(modifier = Modifier.height(80.dp)) // Space for footer
            }
            
            // Bottom Buttons
            BottomTargetPaymentButtons(
                mainGradient = mainGradient,
                onDraftClick = onDraftClick,
                onNextClick = onNextClick,
                isLoading = false
            )
        }
    }
}

/**
 * Visibility Toggle Card Component
 * Thick rounded rectangle with vibrant blue, white text, and toggle switch
 */
@Composable
private fun VisibilityToggleCard(
    title: String,
    subtitle: String?,
    isToggled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF2196F3), // Vibrant blue
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onToggle() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFE3F2FD) // Light blue
                    )
                }
            }
            
            // Toggle Switch (white track, black knob)
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(52.dp, 32.dp)
                    .background(
                        color = if (isToggled) Color.White else Color(0xFF90CAF9),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(3.dp),
                contentAlignment = if (isToggled) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(
                            color = if (isToggled) Color.Black else Color.White,
                            shape = RoundedCornerShape(13.dp)
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TargetAndPaymentsScreenPreview() {
    TargetAndPaymentsScreen()
}
