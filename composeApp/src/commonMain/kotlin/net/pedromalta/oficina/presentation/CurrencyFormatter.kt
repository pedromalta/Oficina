package net.pedromalta.oficina.presentation

fun formatAsMoney(digits: String): String {
    val cents = digits.toLongOrNull() ?: 0L
    val reais = cents / 100
    val centavos = cents % 100
    return "R$ $reais,${centavos.toString().padStart(2, '0')}"
}
