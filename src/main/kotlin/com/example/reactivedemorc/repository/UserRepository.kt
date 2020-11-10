package com.example.reactivedemorc.repository

import com.example.reactivedemorc.entity.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.UUID

interface UserRepository : ReactiveCrudRepository<User, UUID>