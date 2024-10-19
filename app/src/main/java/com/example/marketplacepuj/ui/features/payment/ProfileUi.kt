package com.example.marketplacepuj.ui.features.payment

import com.example.marketplacepuj.util.splitStringWithDivider

data class ProfileUi(
    val fullName: String,
    val nickName: String,
    val id: String,
    val email: String,
    val profilePicUrl: String,
    val tier: String,
) {
    companion object {
        fun mock() = ProfileUi(
            fullName = "Alexander Michael",
            nickName = "@alexandermichael",
            id = "0896 2102 7821",
            email = "test@example.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = "Basic"
        )

        fun mapFromDomain(profile: CompactProfile) = ProfileUi(
            fullName = "${profile.firstName} ${profile.lastName}",
            nickName = profile.nickName,
            id = profile.id.splitStringWithDivider(),
            email = profile.email,
            profilePicUrl = profile.profilePicUrl,
            tier = when (profile.tier) {
                ProfileTier.BASIC -> "Basic"
                ProfileTier.PREMIUM -> "Premium"
            }
        )
    }
}
