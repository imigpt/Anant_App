package com.example.anantapp.presentation.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.EnableLocationViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import android.location.Location
import kotlin.coroutines.resume

@Composable
fun EnableLocationScreen(
    onSkip: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: EnableLocationViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val defaultLocation = remember { LatLng(28.6139, 77.2090) }
    var selectedLocation by remember { mutableStateOf(defaultLocation) }
    var selectedAddressTitle by remember { mutableStateOf("Current Area") }
    var suggestions by remember { mutableStateOf<List<AddressSuggestion>>(emptyList()) }
    var showSuggestions by remember { mutableStateOf(false) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }
    val fusedLocationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        hasLocationPermission = granted
        if (granted) {
            scope.launch {
                val location = getLastKnownOrCurrentLocation(context, fusedLocationClient)
                if (location != null) {
                    val current = LatLng(location.latitude, location.longitude)
                    selectedLocation = current
                    selectedAddressTitle = "Current Location"
                    viewModel.setLocationData(location.latitude, location.longitude, state.address)
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(current, 15f),
                        durationMs = 1000
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        hasLocationPermission = fineGranted || coarseGranted

        if (fineGranted || coarseGranted) {
            scope.launch {
                val location = getLastKnownOrCurrentLocation(context, fusedLocationClient)
                if (location != null) {
                    val current = LatLng(location.latitude, location.longitude)
                    selectedLocation = current
                    selectedAddressTitle = "Current Location"
                    viewModel.setLocationData(location.latitude, location.longitude, state.address)
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(current, 15f),
                        durationMs = 1000
                    )
                }
            }
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            scope.launch { onSuccess() }
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(state.address) {
        val query = state.address.trim()
        if (query.length < 3) {
            suggestions = emptyList()
            showSuggestions = false
            return@LaunchedEffect
        }

        delay(300)
        val result = fetchAddressSuggestions(context, query)
        suggestions = result
        showSuggestions = true

        if (result.isNotEmpty()) {
            val bestMatch = result.first()
            selectedLocation = bestMatch.location
            selectedAddressTitle = bestMatch.title
            // Animate camera to new location
            scope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(bestMatch.location, 15f),
                    durationMs = 1000
                )
            }
        }
    }

    // Main background container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)) // Light gray background
    ) {
        // Decorative solid green circles in the background with linear gradient
        Canvas(modifier = Modifier.fillMaxSize()) {
            val blobGradient = Brush.linearGradient(
                colors = listOf(Color(0xFFBCFE37), Color(0xFF82B027)),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )

            // Top Right Blob
            drawCircle(
                brush = blobGradient,
                center = Offset(size.width - 50f, 150f),
                radius = 200f,
                alpha = 0.8f
            )
            // Bottom Left Blob
            drawCircle(
                brush = blobGradient,
                center = Offset(100f, size.height - 150f),
                radius = 150f,
                alpha = 0.8f
            )
        }

        // Main Card with Glassmorphism effect
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .border(
                    width = 1.5.dp,
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(32.dp)
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.7f) // Semi-transparent for glassmorphism
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Skip Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { onSkip() }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Skip >>",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Location Icon
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location Pin",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Enable Precise\nLocation",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "This keeps your profile safe, ensures\nfaster help during emergencies.",
                    fontSize = 13.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Google Map Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            isMyLocationEnabled = hasLocationPermission
                        )
                    ) {
                        Marker(
                            state = MarkerState(position = selectedLocation),
                            title = selectedAddressTitle
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Address Input Field with Gradient Outline
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(28.dp))
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF8A2BE2), // Purple
                                    Color(0xFFFF1493), // Deep Pink
                                    Color(0xFFFF8C00)  // Orange
                                )
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                ) {
                    OutlinedTextField(
                        value = state.address,
                        onValueChange = {
                            viewModel.updateAddress(it)
                            showSuggestions = true
                        },
                        modifier = Modifier.fillMaxSize(),
                        placeholder = {
                            Text(
                                "Enter Address",
                                fontSize = 14.sp,
                                color = Color(0xFF888888)
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(28.dp),
                        singleLine = true
                    )
                }

                if (showSuggestions) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 180.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            if (suggestions.isEmpty()) {
                                Text(
                                    text = "No address found for this input",
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                    fontSize = 13.sp,
                                    color = Color(0xFF777777)
                                )
                            } else {
                                suggestions.forEach { suggestion ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.updateAddress(suggestion.title)
                                                selectedLocation = suggestion.location
                                                selectedAddressTitle = suggestion.title
                                                scope.launch {
                                                    cameraPositionState.animate(
                                                        CameraUpdateFactory.newLatLngZoom(suggestion.location, 15f),
                                                        durationMs = 1000
                                                    )
                                                }
                                                showSuggestions = false
                                                suggestions = emptyList()
                                            }
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.LocationOn,
                                            contentDescription = "Suggestion",
                                            tint = Color(0xFF666666),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = suggestion.title,
                                            fontSize = 13.sp,
                                            color = Color(0xFF333333),
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Enable Location Button
                Button(
                    onClick = { 
                        viewModel.enableLocationServices()
                        onSuccess() // Navigate to FamilyDetailsScreen
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFCF11) // Green
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Enable Location",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Privacy Footer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Privacy Lock",
                        tint = Color(0xFFB0B0B0),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your data stays private & encrypted.",
                        fontSize = 12.sp,
                        color = Color(0xFFB0B0B0),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private suspend fun getLastKnownOrCurrentLocation(
    context: Context,
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient
): Location? {
    val fineGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val coarseGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!fineGranted && !coarseGranted) return null

    val lastLocation = suspendCancellableCoroutine<Location?> { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { continuation.resume(it) }
            .addOnFailureListener { continuation.resume(null) }
    }
    if (lastLocation != null) return lastLocation

    val priority = if (fineGranted) {
        Priority.PRIORITY_HIGH_ACCURACY
    } else {
        Priority.PRIORITY_BALANCED_POWER_ACCURACY
    }

    return suspendCancellableCoroutine { continuation ->
        fusedLocationClient.getCurrentLocation(priority, null)
            .addOnSuccessListener { continuation.resume(it) }
            .addOnFailureListener { continuation.resume(null) }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EnableLocationScreenPreview() {
    EnableLocationScreen(onSkip = {}, onSuccess = {})
}

private data class AddressSuggestion(
    val title: String,
    val location: LatLng
)

private suspend fun fetchAddressSuggestions(context: Context, query: String): List<AddressSuggestion> {
    val geocoder = Geocoder(context, Locale.getDefault())

    return try {
        val addresses: List<Address> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                geocoder.getFromLocationName(query, 5) { result ->
                    continuation.resume(result ?: emptyList())
                }
            }
        } else {
            withContext(Dispatchers.IO) {
                runCatching { geocoder.getFromLocationName(query, 5).orEmpty() }
                    .getOrDefault(emptyList())
            }
        }

        addresses.mapNotNull { address ->
            val lat = address.latitude
            val lng = address.longitude
            val title = address.getAddressLine(0)
                ?: listOfNotNull(address.featureName, address.locality, address.adminArea)
                    .joinToString(", ")

            if (title.isBlank()) {
                null
            } else {
                AddressSuggestion(title = title, location = LatLng(lat, lng))
            }
        }
    } catch (e: Exception) {
        emptyList()
    }
}
