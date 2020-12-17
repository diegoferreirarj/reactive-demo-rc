package com.example.reactivedemorc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("users")
data class User(
        @Id val id: Int? = null,
        val name: String,
        val createdAt: Instant? = Instant.now()
)