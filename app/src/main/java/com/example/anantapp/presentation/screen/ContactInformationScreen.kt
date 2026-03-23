package com.example.anantapp.presentation.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.anantapp.ui.components.CurvedBottomShape

/**
 * Contact Information Screen
 * Displays form for contact information updates
 */
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
    var panCard by remember { mutableStateOf("PNA123ASD2314") }
    var gender by remember { mutableStateOf("Male") }
    var age by remember { mutableStateOf("23") }
    var phoneNumber by remember { mutableStateOf("+91 1234567890") }
    var alternatePhoneNumber by remember { mutableStateOf("+91 1234567890") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    
    // Remember the custom shape to avoid re-calculating or potential class loading issues in preview
    val curvedShape = remember { CurvedBottomShape() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // ==================== Main Content ====================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ==================== Light Pink Header ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(Color(0xFFFFF0FE))
                    .clip(curvedShape)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // ========== Status Bar ==========
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back Button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, CircleShape)
                                .border(1.dp, Color(0xFFE91E63), CircleShape)
                                .clickable { onBackClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFFE91E63),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Title
                        Text(
                            text = "Profile Settings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        // Notification Bell
                        Box(
                            modifier = Modifier.size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color(0xFFE91E63),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ========== User Info Row ==========
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // User Details
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Hello $userName,",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333),
                                lineHeight = 30.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Anant Id: $anantId",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "badge icon or image here",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF999999)
                            )
                        }

                        // Profile Picture Circle
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF9C27B0),
                                            Color(0xFFE91E63)
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .background(Color(0xFFF5F5F5), shape = CircleShape)
                                    .clip(CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                // Fix smart cast issue by using .let on the delegated property
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

            // ==================== Form Section ====================
            // Pink Gradient Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE91E63))
                    .padding(16.dp)
            ) {
                Text(
                    text = "CONTACT INFORMATION",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== Form Fields ====================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Blood Group
                FormField(
                    label = "Blood Group",
                    value = bloodGroup,
                    onValueChange = { bloodGroup = it }
                )

                // Profile Image Upload
                ImageUploadField(
                    imageUri = profileImageUri,
                    onImageSelected = { profileImageUri = it },
                    onImageRemove = { profileImageUri = null }
                )

                // Email
                FormField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it }
                )

                // Category
                FormField(
                    label = "Category",
                    value = category,
                    onValueChange = { category = it }
                )

                // Address
                FormField(
                    label = "Address",
                    value = address,
                    onValueChange = { address = it }
                )

                // Aadhar Card
                FormField(
                    label = "Aadhar Card",
                    value = aadharCard,
                    onValueChange = { aadharCard = it }
                )

                // PAN Card
                FormField(
                    label = "PAN Card",
                    value = panCard,
                    onValueChange = { panCard = it }
                )

                // Gender
                FormField(
                    label = "Gender",
                    value = gender,
                    onValueChange = { gender = it }
                )

                // Age
                FormField(
                    label = "Age",
                    value = age,
                    onValueChange = { age = it }
                )

                // Phone Number
                FormFieldWithFlag(
                    label = "Phone No.",
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it }
                )

                // Alternate Phone Number
                FormFieldWithFlag(
                    label = "Alternate Phone No.",
                    value = alternatePhoneNumber,
                    onValueChange = { alternatePhoneNumber = it }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==================== Update Button ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF7C3AED),
                                Color(0xFFE91E63)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .clickable { onUpdateClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Request to Update Profile",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Form Field Component
 * Reusable text field with magenta border and label
 */
@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Label
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        // Input Field
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFE91E63),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color(0xFFE91E63),
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = androidx.compose.material3.LocalTextStyle.current.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            singleLine = true
        )
    }
}

/**
 * Form Field with Indian Flag
 * Phone number field with flag on the left
 */
@Composable
private fun FormFieldWithFlag(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Label
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        // Input Field with Flag
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFE91E63),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Indian Flag (using simple colored blocks)
            Row(
                modifier = Modifier
                    .width(28.dp)
                    .height(18.dp)
                    .clip(RoundedCornerShape(2.dp))
            ) {
                Box(modifier = Modifier
                    .height(18.dp)
                    .weight(1f)
                    .background(Color(0xFFFF9933)))
                Box(modifier = Modifier
                    .height(18.dp)
                    .weight(1f)
                    .background(Color.White))
                Box(modifier = Modifier
                    .height(18.dp)
                    .weight(1f)
                    .background(Color(0xFF138808)))
            }

            // Text Field
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                textStyle = androidx.compose.material3.LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                singleLine = true
            )
        }
    }
}

/**
 * Image Upload Field Component
 * Displays image upload box or uploaded image
 */
@Composable
private fun ImageUploadField(
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Label
        Text(
            text = "Update your profile Image",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        // Upload Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFE91E63),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                // Show uploaded image
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Selected profile image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Show remove button
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .padding(8.dp)
                                .background(Color(0xFFE91E63), CircleShape)
                                .clickable { onImageRemove() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove image",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            } else {
                // Show upload icon
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFFF5F5F5), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Upload photo",
                            tint = Color(0xFFCCCCCC),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = "click here to update your profile image",
                        fontSize = 12.sp,
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun ContactInformationScreenPreview() {
    ContactInformationScreen()
}
