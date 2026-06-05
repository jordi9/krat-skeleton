package com.jordi9.skeleton.shared.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import kotlin.random.Random

class NanoIdTest : StringSpec({

  "generate URL-friendly IDs with the requested prefix and default NanoID size" {
    val id = NanoId(Random(42)).get("item_")

    id shouldMatch Regex("^item_[A-Za-z0-9_-]{21}$")
  }

  "generate unique IDs across a sample" {
    val generator = NanoId(Random(42))
    val ids = (1..1_000).map { generator.get("item_") }

    ids.toSet().size shouldBe ids.size
  }

  "validate IDs by prefix, default NanoID size, and URL-friendly suffix" {
    val validSuffix = "a".repeat(21)

    NanoId.isValid("item_", "item_$validSuffix") shouldBe true
    NanoId.isValid("item_", "other_$validSuffix") shouldBe false
    NanoId.isValid("item_", "item_${"a".repeat(20)}") shouldBe false
    NanoId.isValid("item_", "item_${"a".repeat(22)}") shouldBe false
    NanoId.isValid("item_", "item_${"a".repeat(20)}!") shouldBe false
  }

  "validate every URL-friendly alphabet character" {
    val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-"

    alphabet.forEach { char ->
      NanoId.isValid("item_", "item_${char.toString().repeat(21)}") shouldBe true
    }

    "+/=:!".forEach { char ->
      NanoId.isValid("item_", "item_${char.toString().repeat(21)}") shouldBe false
    }
  }
})
