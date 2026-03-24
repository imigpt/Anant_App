package com.example.anantapp.data.model

data class OrderSuccessFormState(
    val deliveryAddress: String = "Horizon Towers Near World\nTrade Park Jaipur",
    val estimatedDelivery: String = "5-7 days.",
    val qrStickerId: String = "",
    val orderDate: String = ""
)
