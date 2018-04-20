package com.example.demo.controller

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache.ValueRetrievalException
import org.springframework.cache.CacheManager
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.SecureRandom
import java.util.Random

private val logger = LoggerFactory.getLogger(DemoController::class.java)

private val random: Random = SecureRandom.getInstanceStrong()

@RestController
class DemoController(cacheManager: CacheManager) {

    private val cache = cacheManager.getCache("VALUES")!!

    init {
        println(
            """

            ----------------

            Cache:
            ${cache.nativeCache.javaClass.name}

            ----------------

            """.trimIndent()
        )
    }

    @GetMapping("/{key}")
    fun getCachedValue(@PathVariable key: String): Int? {

        try {
            return cache.get(key, random::nextInt)
        } catch (ex: ValueRetrievalException) {
            logger.error(ex.message, ex)
            return null
        }
    }

    @PutMapping("/{key}")
    fun addCachedValue(@PathVariable key: String, @RequestBody value: Int) {

        cache.put(key, value)
    }

    @DeleteMapping("/{key}")
    fun removeCachedValue(@PathVariable key: String) {

        cache.evict(key)
    }
}
