package com.jordi9.skeleton.feature.greeting.inbound

import kotlinx.serialization.Serializable

@Serializable
data class GreetingConfig(val message: String)
