package net.pedromalta.oficina.domain

data class Shop(
    val name: String,
    val address: Address,
    val phone: String,
    val CNPJ: String,
)

data class Address(
    val street: String,
    val number: String,
    val neighborhood: String,
    val city: String,
    val state: String,
    val zipCode: String,
)