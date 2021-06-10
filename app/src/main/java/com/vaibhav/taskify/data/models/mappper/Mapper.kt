package com.vaibhav.taskify.data.models.mappper

interface Mapper<Network, Domain> {

    fun toDomainModel(network: Network): Domain

    fun toDomainList(network: List<Network>): List<Domain>

    fun toNetwork(domain: Domain): Network
}