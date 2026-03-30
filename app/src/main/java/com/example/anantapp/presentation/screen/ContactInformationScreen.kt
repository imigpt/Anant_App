package com.example.anantapp.presentation.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ContactInformationScreen(
    userName: String = "Mahendra",
    anantId: String = "#9121038605",
    onBackClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {}
) {
    // Form state
    var bloodGroup by remember { mutableStateOf("B+") }
    var email by remember { mutableStateOf("mahendra.designs@imigpt.com") }
    var category by remember { mutableStateOf("Businessman") }
    var address by remember { mutableStateOf("611, 6th floor, Aselea Networks, Horizon Tower, Jaipur") }
    var aadharCard by remember { mutableStateOf("1234-1234-1234") }
    var panCard by remember { mutableStateOf("PNA123ASD23") }
    var gender by remember { mutableStateOf("Male") }
    var age by remember { mutableStateOf("23") }
    var phoneNumber by remember { mutableStateOf("1234567890") }
    var alternatePhoneNumber by remember { mutableStateOf("1234567890") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val density = LocalDensity.current
    val curveDepth = remember(density) { with(density) { 48.dp.toPx() } }

    // Custom shape for the soft curved bottom of the header
    val curvedShape = remember(curveDepth) {
        GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - curveDepth)
            quadraticBezierTo(
                size.width / 2f, size.height + curveDepth,
                0f, size.height - curveDepth
            )
            close()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // ==================== Light Pink Curved Header ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(curvedShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFDF0F6), // Very light pinkish-white
                            Color(0xFFFBE4F3)  // Soft light pink
                        )
                    )
                )
        ) {
            // Decorative overlapping circle on the top left
            Canvas(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = (-40).dp, y = (-20).dp)
            ) {
                drawCircle(color = Color(0xFFF4D2EB).copy(alpha = 0.5f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                // ========== Top Bar ==========
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back Button inside circle
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
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Text(
                        text = "Profile Settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ========== User Info Row ==========
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Side: Name and ID
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Hello, ",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9C27B0) // Purple
                            )
                            Text(
                                text = userName,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFE91E63) // Pink
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Anant Id: $anantId",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }

                    // Right Side: Profile Picture
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63))
                                ),
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color(0xFFF5F5F5), CircleShape)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            profileImageUri?.let { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } ?: Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Profile Picture",
                                tint = Color(0xFFCCCCCC),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }

        // ==================== Pink Section Header ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF5B91)) // Vibrant Pink matching image
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "CONTACT INFORMATION",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==================== Form Fields ====================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomFormField(label = "Blood Group", value = bloodGroup, onValueChange = { bloodGroup = it })

            CustomImageUploadField(
                imageUri = profileImageUri,
                onImageSelected = { profileImageUri = it },
                onImageRemove = { profileImageUri = null }
            )

            CustomFormField(label = "Email", value = email, onValueChange = { email = it })
            CustomFormField(label = "Category", value = category, onValueChange = { category = it })
            CustomFormField(label = "Address", value = address, onValueChange = { address = it })
            CustomFormField(label = "Aadhar Card", value = aadharCard, onValueChange = { input ->
                // Aadhar format: 12 digits with format XXXX-XXXX-XXXX
                val filtered = input.filter { it.isDigit() }.take(12)
                val formatted = when {
                    filtered.length <= 4 -> filtered
                    filtered.length <= 8 -> filtered.substring(0, 4) + "-" + filtered.substring(4)
                    else -> filtered.substring(0, 4) + "-" + filtered.substring(4, 8) + "-" + filtered.substring(8)
                }
                aadharCard = formatted
            })
            CustomFormField(label = "PAN Card", value = panCard, onValueChange = { input ->
                // PAN format: 10 uppercase alphanumeric characters only
                val filtered = input.filter { it.isLetterOrDigit() }.take(10).uppercase()
                panCard = filtered
            })
            CustomFormField(label = "Gender", value = gender, onValueChange = { gender = it })
            CustomFormField(label = "Age", value = age, onValueChange = { age = it })
            CustomPhoneField(label = "Phone No.", value = phoneNumber, onValueChange = { input ->
                // Phone: 10 digits only for Indian number
                val filtered = input.filter { it.isDigit() }.take(10)
                phoneNumber = filtered
            })
            CustomPhoneField(label = "Alternate Phone No.", value = alternatePhoneNumber, onValueChange = { input ->
                // Alternate Phone: 10 digits only for Indian number
                val filtered = input.filter { it.isDigit() }.take(10)
                alternatePhoneNumber = filtered
            })
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ==================== Update Button ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(99.dp), spotColor = Color(0xFFE91E63))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264))
                    ),
                    shape = RoundedCornerShape(99.dp)
                )
                .clickable { onUpdateClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Request to Update Profile",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

/**
 * Custom clean form field to replicate the design exactly
 */
@Composable
private fun CustomFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264))
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(1.dp)
                .background(Color.White, RoundedCornerShape(7.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Phone Number field containing the horizontal Indian Flag UI
 */
@Composable
private fun CustomPhoneField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF1493))
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(1.dp)
                .background(Color.White, RoundedCornerShape(7.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Horizontal Indian Flag
            Column(
                modifier = Modifier
                    .width(26.dp)
                    .height(18.dp)
                    .border(0.5.dp, Color.LightGray, RoundedCornerShape(2.dp))
                    .clip(RoundedCornerShape(2.dp))
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color(0xFFFF9933))) // Saffron
                Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color.White))
                Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color(0xFF138808))) // Green
            }

            Spacer(modifier = Modifier.width(12.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Image Upload Box with a red outline "X" in the top right
 */
@Composable
private fun CustomImageUploadField(
    imageUri: Uri? = null,
    onImageSelected: (Uri) -> Unit = {},
    onImageRemove: () -> Unit = {}
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Update your profile Image",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF1493))
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(1.dp)
                .background(Color.White, RoundedCornerShape(7.dp))
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Selected profile image",
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Remove Button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(24.dp)
                            .background(Color.White, CircleShape)
                            .clickable { onImageRemove() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = "Remove",
                            tint = Color.Red,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            } else {
                // Empty State with Icon + Top Right Red Cross
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Upload",
                            tint = Color.Gray,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "click here to update your profile image",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }

                    // Top right small red indicator x (matches the design mockup exactly)
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "Error Indicator",
                        tint = Color.Red,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(18.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
internal fun ContactInformationScreenPreview() {
    ContactInformationScreen()
}
