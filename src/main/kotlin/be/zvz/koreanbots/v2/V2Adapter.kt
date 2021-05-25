/*
 * Copyright 2021 JellyBrick, nkgcp, and all its contributor. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package be.zvz.koreanbots.v2

import be.zvz.koreanbots.ApiAdapter
import be.zvz.koreanbots.RequestFailedException
import be.zvz.koreanbots.dto.Bot
import be.zvz.koreanbots.dto.User
import be.zvz.koreanbots.dto.Voted
import be.zvz.koreanbots.v2.dto.BotImpl
import be.zvz.koreanbots.v2.dto.ResponseWrapper
import be.zvz.koreanbots.v2.dto.UserImpl
import be.zvz.koreanbots.v2.dto.VotedImpl
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.jackson.objectBody
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getOrElse

class V2Adapter internal constructor(
    private val botId: String,
    private val token: String,
    private val mapper: ObjectMapper,
    private val fuelManager: FuelManager
) : ApiAdapter {

    override fun getBotInfo(targetBotId: String): Bot = handleResponse(
        fuelManager
            .get("/v2/bots/$targetBotId")
            .responseObject<ResponseWrapper<BotImpl>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    override fun getBotInfo(targetBotId: String, onSuccess: (Bot) -> Unit, onFailure: ((Throwable) -> Unit)?) {
        fuelManager
            .get("/v2/bots/$targetBotId")
            .responseObject<ResponseWrapper<BotImpl>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    override fun checkUserVote(userId: String): Voted = handleResponse(
        fuelManager
            .get("/v2/bots/$botId/vote", listOf("userID" to userId))
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ResponseWrapper<VotedImpl>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    override fun checkUserVote(userId: String, onSuccess: (Voted) -> Unit, onFailure: ((Throwable) -> Unit)?) {
        fuelManager
            .get("/v2/bots/$botId/vote", listOf("userID" to userId))
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ResponseWrapper<VotedImpl>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    override fun updateBotServers(servers: Int) {
        handleResponse(
            fuelManager
                .post("/v2/bots/$botId/stats")
                .header(Headers.AUTHORIZATION, token)
                .objectBody(mapOf("servers" to servers), mapper = mapper)
                .responseObject<ResponseWrapper<Unit>>(mapper = mapper)
                .third
        )
    }

    override fun updateBotServers(servers: Int, onSuccess: () -> Unit, onFailure: ((Throwable) -> Unit)?) {
        fuelManager
            .post("/v2/bots/$botId/stats")
            .header(Headers.AUTHORIZATION, token)
            .objectBody(mapOf("servers" to servers), mapper = mapper)
            .responseObject<ResponseWrapper<Unit>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke() }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    override fun getUserInfo(userId: String): User = handleResponse(
        fuelManager
            .get("/v2/users/$userId")
            .responseObject<ResponseWrapper<UserImpl>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    override fun getUserInfo(userId: String, onSuccess: (User) -> Unit, onFailure: ((Throwable) -> Unit)?) {
        fuelManager
            .get("/v2/users/$userId")
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ResponseWrapper<UserImpl>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    private fun <T> handleResponse(result: Result<ResponseWrapper<T>, FuelError>): T? =
        result.getOrElse {
            throw mapper.readValue<RequestFailedException>(it.errorData)
        }
            .data

    // /////////////////////////////
    //   Unsupported Functions   //
    // /////////////////////////////
    override fun getBotList(page: Int): List<Bot> {
        throw UnsupportedOperationException("Only Supported on API v1")
    }

    override fun getBotList(page: Int, onSuccess: (List<Bot>) -> Unit, onFailure: ((Throwable) -> Unit)?) {
        throw UnsupportedOperationException("Only Supported on API v1")
    }
}