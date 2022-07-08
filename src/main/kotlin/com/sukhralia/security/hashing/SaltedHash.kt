package com.sukhralia.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
