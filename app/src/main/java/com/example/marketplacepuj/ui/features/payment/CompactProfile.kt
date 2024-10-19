package com.example.marketplacepuj.ui.features.payment

data class CompactProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nickName: String,
    val email: String,
    val profilePicUrl: String,
    val tier: ProfileTier,
)