package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.LocalTextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.DeliveryAddressViewModel

// ==================== Data Classes ====================

data class DeliveryAddressFormState(
    val deliveryName: String = "",
    val addressType: String = "Home", // "Home" or "Work"
    val houseNumber: String = "",
    val buildingName: String = "",
    val streetLocality: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val mobileNumber: String = "",
    val alternateMobileNumber: String = "",
    val isHomeSelected: Boolean = true,
    val isWorkSelected: Boolean = false
)

// ==================== Main Screen ====================

@Composable
fun DeliveryAddressScreen(
    viewModel: DeliveryAddressViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNextClick: (DeliveryAddressFormState) -> Unit = {}
) {
    val formState by viewModel.formState.collectAsState()
    DeliveryAddressContent(
        formState = formState,
        onDeliveryNameChange = { viewModel.updateDeliveryName(it) },
        onAddressTypeChange = { viewModel.updateAddressType(it) },
        onHouseNumberChange = { viewModel.updateHouseNumber(it) },
        onBuildingNameChange = { viewModel.updateBuildingName(it) },
        onStreetLocalityChange = { viewModel.updateStreetLocality(it) },
        onCityChange = { viewModel.updateCity(it) },
        onStateChange = { viewModel.updateState(it) },
        onPincodeChange = { viewModel.updatePincode(it) },
        onMobileNumberChange = { viewModel.updateMobileNumber(it) },
        onAlternateMobileNumberChange = { viewModel.updateAlternateMobileNumber(it) },
        onBackClick = onBackClick,
        onGetQRCodeClick = { onNextClick(viewModel.getFormData()) }
    )
}

@Composable
fun DeliveryAddressContent(
    formState: DeliveryAddressFormState,
    onDeliveryNameChange: (String) -> Unit,
    onAddressTypeChange: (String) -> Unit,
    onHouseNumberChange: (String) -> Unit,
    onBuildingNameChange: (String) -> Unit,
    onStreetLocalityChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onStateChange: (String) -> Unit,
    onPincodeChange: (String) -> Unit,
    onMobileNumberChange: (String) -> Unit,
    onAlternateMobileNumberChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onGetQRCodeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Decorative blue gradient blurred circles
        // Top-right circle
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4192EF),
                            Color(0xFF006FEE)
                        )
                    ),
                    shape = RoundedCornerShape(100.dp)
                )
                .blur(40.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-60).dp)
        )

        // Bottom-left circle
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4192EF),
                            Color(0xFF006FEE)
                        )
                    ),
                    shape = RoundedCornerShape(90.dp)
                )
                .blur(35.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-60).dp, y = 80.dp)
        )


        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
                    .blur(0.5.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header Section
                    HeaderSectionDelivery()

                    // Delivery Name Input
                    BlueGradientInputField(
                        value = formState.deliveryName,
                        onValueChange = onDeliveryNameChange,
                        placeholder = "Delivery Name",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Delivery Address Label & Type Toggle
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Delivery Address",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )

                        // Home/Work Toggle Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AddressTypeToggle(
                                label = "Home",
                                isSelected = formState.isHomeSelected,
                                onToggle = {
                                    onAddressTypeChange("Home")
                                },
                                modifier = Modifier.weight(1f)
                            )

                            AddressTypeToggle(
                                label = "Work",
                                isSelected = formState.isWorkSelected,
                                onToggle = {
                                    onAddressTypeChange("Work")
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // House / Flat Number
                    BlueGradientInputField(
                        value = formState.houseNumber,
                        onValueChange = onHouseNumberChange,
                        placeholder = "House / Flat Number",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Building / Apartment Name
                    BlueGradientInputField(
                        value = formState.buildingName,
                        onValueChange = onBuildingNameChange,
                        placeholder = "Building / Apartment Name",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Street / Locality
                    BlueGradientInputField(
                        value = formState.streetLocality,
                        onValueChange = onStreetLocalityChange,
                        placeholder = "Street / Locality",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // City & State Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BlueGradientInputField(
                            value = formState.city,
                            onValueChange = onCityChange,
                            placeholder = "City",
                            modifier = Modifier.weight(1f)
                        )

                        BlueGradientInputField(
                            value = formState.state,
                            onValueChange = onStateChange,
                            placeholder = "State",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Pincode
                    BlueGradientInputField(
                        value = formState.pincode,
                        onValueChange = onPincodeChange,
                        placeholder = "Pincode",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Number
                    )

                    // Mobile Number with Country Code
                    MobileNumberField(
                        value = formState.mobileNumber,
                        onValueChange = onMobileNumberChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Alternate Mobile Number
                    BlueGradientInputField(
                        value = formState.alternateMobileNumber,
                        onValueChange = onAlternateMobileNumberChange,
                        placeholder = "Alternate Mobile Number",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Disclaimer
                    Text(
                        text = "Your address is used only for secure delivery.\nNever shared elsewhere.",
                        fontSize = 10.sp,
                        color = Color(0xFFB0B0B0),
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Get QR Code Button
                    Button(
                        onClick = onGetQRCodeClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(50.dp)),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Get QR Code",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ==================== Composable Components ====================

@Composable
private fun HeaderSectionDelivery() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Location Pin Icon
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(48.dp),
            tint = Color.Black
        )

        // Title
        Text(
            text = "Enter Delivery Address",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        // Subtitle
        Text(
            text = "Get Your QR Sticker Delivered",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BlueGradientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF006FEE),
                        Color(0xFF78B5FC)
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun MobileNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF006FEE),
                        Color(0xFF78B5FC)
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // India Flag
            Text(
                text = "🇮🇳",
                fontSize = 18.sp
            )

            // Dropdown Arrow
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Dropdown",
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )

            // Divider
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp),
                color = Color.White.copy(alpha = 0.3f)
            )

            // Input Field
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "+91 0000000000",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun AddressTypeToggle(
    label: String,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF999999),
            fontWeight = FontWeight.Medium
        )

        Switch(
            checked = isSelected,
            onCheckedChange = { newState ->
                if (newState) {
                    onToggle()
                }
            },
            modifier = Modifier.size(40.dp, 24.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = Color.Black.copy(alpha = 0.3f),
                uncheckedThumbColor = Color.Black,
                uncheckedTrackColor = Color.Black.copy(alpha = 0.1f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDeliveryAddressScreen() {
    DeliveryAddressContent(
        formState = DeliveryAddressFormState(),
        onDeliveryNameChange = {},
        onAddressTypeChange = {},
        onHouseNumberChange = {},
        onBuildingNameChange = {},
        onStreetLocalityChange = {},
        onCityChange = {},
        onStateChange = {},
        onPincodeChange = {},
        onMobileNumberChange = {},
        onAlternateMobileNumberChange = {},
        onBackClick = {},
        onGetQRCodeClick = {}
    )
}
