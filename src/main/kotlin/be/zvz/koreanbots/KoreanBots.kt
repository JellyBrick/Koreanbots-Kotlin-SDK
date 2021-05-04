/*
 * Copyright 2021 JellyBrick, nkgcp, and all its contributor. All rights reserved.
 *
 * Licensed undr the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions andlimitations under the License.
 */
package be.zvz.koreanbots

import be.zvz.koreanbots.dto.Bot
import be.zvz.koreanbots.dto.ResponseWrapper
import be.zvz.koreanbots.dto.ServersUpdate
import be.zvz.koreanbots.dto.User
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.jackson.objectBody
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getOrElse

class KoreanBots(val token: String) {
    fun getBotInfo(id: String): Bot {
        val (_, _, rst) = Fuel.get("/bots/$id")
            .responseObject<ResponseWrapper<Bot>>(mapper = mapper)

        return handleResponse(rst)
            ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error
    }

    fun updateBotServers(id: String, servers: Int) {
        val (_, _, rst) = Fuel.post("/bots/$id/stats")
            .header(Headers.AUTHORIZATION, token)
            .objectBody(ServersUpdate(servers), mapper = mapper)
            .responseObject<ResponseWrapper<Unit>>(mapper = mapper)

        handleResponse(rst)
    }

    fun getUserInfo(id: String): User {
        val (_, _, rst) = Fuel.get("/users/$id")
            .responseObject<ResponseWrapper<User>>(mapper = mapper)

        return handleResponse(rst)
            ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error
    }

    private fun <T> handleResponse(result: Result<ResponseWrapper<T>, FuelError>): T? {
        val wrapper = result.getOrElse { throw RequestFailedException(it.message.orEmpty()) }

        if (wrapper.code !in 200..299)
            throw RequestFailedException("API responded ${wrapper.code}: ${wrapper.message}")

        return wrapper.data
    }

    companion object {
        const val BASE_URL = "https://koreanbots.dev/api/v2"

        private val mapper = ObjectMapper()
            .registerKotlinModule()
            .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)

        init {
            FuelManager.instance.basePath = BASE_URL
            FuelManager.instance.baseHeaders = mapOf(
                Headers.USER_AGENT to "Kotlin SDK(alpha, https://github.com/nkgcp/Koreanbots-Kotlin-SDK)"
            )
        }
    }
}
