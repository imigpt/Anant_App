package com.example.anantapp.data.model

data class OrderStatusFormState(
    val orderId: String = "#123456",
    val status: String = "Packed",
    val expectedDelivery: String = "8 July 2025",
    val orderDate: String = "5 July 2025",
    val paymentStatus: String = "Paid",
    val trackingUrl: String = "",
    val qrCode: String = ""
)
