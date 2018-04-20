package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.security.SecureRandom
import java.util.Random

private val random: Random = SecureRandom.getInstanceStrong()

@RestController
class DemoController {

    @GetMapping("/{key}")
    fun getValue(@PathVariable key: String): Int? {

        return random.nextInt()
    }
}
