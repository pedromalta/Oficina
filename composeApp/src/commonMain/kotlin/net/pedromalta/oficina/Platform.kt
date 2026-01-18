package net.pedromalta.oficina

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform