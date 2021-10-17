package com.vaibhav.taskify.data.models.entity

import com.vaibhav.taskify.util.Avatars

data class UserEntity(
    val username: String = "",
    val email: String = "",
    val profileImage: String = "",
    val hasCustomImage: Boolean = false,
    val customImageType: Avatars = Avatars.AVATAR_1
)
