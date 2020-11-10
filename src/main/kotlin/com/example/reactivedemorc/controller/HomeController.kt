package com.example.reactivedemorc.controller

import com.example.reactivedemorc.entity.User
import com.example.reactivedemorc.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/")
class HomeController(
    val userRepository: UserRepository
) {

    @GetMapping
    fun hello() = Mono.just("Hello World!")

    @GetMapping("/users")
    fun allUsers() = userRepository.findAll()

    @GetMapping("/users/{name}")
    fun allUsers(@PathVariable("name") name: String) = userRepository.save(User(name=name))

}