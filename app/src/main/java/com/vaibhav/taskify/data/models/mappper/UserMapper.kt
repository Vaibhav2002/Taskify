package com.vaibhav.taskify.data.models.mappper

import com.vaibhav.taskify.data.models.entity.UserEntity
import com.vaibhav.taskify.data.remote.harperDb.User


class UserMapper : Mapper<User, UserEntity> {
    override fun toDomainModel(network: User): UserEntity = UserEntity(
        username = network.username,
        email = network.email,
        profileImage = network.profile_img,
        hasCustomImage = network.profile_img != "",
    )

    override fun toDomainList(network: List<User>): List<UserEntity> =
        network.map { toDomainModel(it) }

    override fun toNetwork(domain: UserEntity): User = User(
        username = domain.username,
        email = domain.email,
        profile_img = domain.profileImage
    )
}

