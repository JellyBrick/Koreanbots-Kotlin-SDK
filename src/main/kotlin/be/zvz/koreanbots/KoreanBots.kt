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
package be.zvz.koreanbots

import be.zvz.koreanbots.dto.Bot
import be.zvz.koreanbots.dto.User
import be.zvz.koreanbots.dto.Voted
import be.zvz.koreanbots.exception.InvalidDataReceivedException
import be.zvz.koreanbots.exception.RequestFailedException
import be.zvz.koreanbots.v1.V1Adapter
import be.zvz.koreanbots.v2.V2Adapter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers

class KoreanBots @JvmOverloads constructor(
    botId: String,
    token: String,
    apiVersion: Int = 1,
    mapper: ObjectMapper = JsonMapper()
        .registerKotlinModule()
        .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING),
    fuelManager: FuelManager = FuelManager()
) {
    private val adapter: ApiAdapter

    init {
        fuelManager.baseHeaders = mapOf(
            Headers.USER_AGENT to "Kotlin SDK(${KoreanBotsInfo.VERSION}, ${KoreanBotsInfo.GITHUB_URL})"
        )

        adapter = when (apiVersion) {
            1 -> {
                fuelManager.basePath = KoreanBotsInfo.API_v1_BASE_URL
                V1Adapter(botId, token, mapper, fuelManager)
            }
            2 -> {
                fuelManager.basePath = KoreanBotsInfo.API_v2_BASE_URL
                V2Adapter(botId, token, mapper, fuelManager)
            }
            else -> throw IllegalArgumentException("Please Select API Version between 1 to 2")
        }
    }

    /**
     * [Bot] 한국 디스코드봇 리스트에 등록된 봇 목록을 받아옵니다. API v1에서만 지원합니다.
     * @param page 봇 목록의 페이지입니다.
     * @throws [RequestFailedException] 요청이 실패한 경우 [RequestFailedException]을 던집니다.
     * @throws [InvalidDataReceivedException] 요청이 성공했으나, 응답받은 객체에 문제가 있을 경우 [InvalidDataReceivedException]을 던집니다.
     * @return [Bot] 봇 목록을 반환합니다.
     */
    @Throws(RequestFailedException::class, InvalidDataReceivedException::class)
    fun getBotList(page: Int): List<Bot> =
        adapter.getBotList(page)

    /**
     * [Bot] 한국 디스코드봇 리스트에 등록된 봇 목록을 비동기적으로 받아옵니다. API v1에서만 지원합니다.
     * @param page 봇 목록의 페이지입니다.
     * @param onSuccess 요청이 성공한 경우 호출됩니다.
     * @param onFailure 요청이 실패한 경우 호출됩니다. null인 경우 아무 동작도 하지 않습니다. 기본값은 null입니다.
     */
    @JvmOverloads
    fun getBotList(page: Int, onSuccess: (List<Bot>) -> Unit, onFailure: ((Throwable) -> Unit)? = null) =
        adapter.getBotList(page, onSuccess, onFailure)

    /**
     * [Bot] 봇 정보를 받아옵니다.
     * @param targetBotId 받아올 봇의 ID 입니다.
     * @throws [RequestFailedException] 요청이 실패한 경우 [RequestFailedException]을 던집니다.
     * @throws [InvalidDataReceivedException] 요청이 성공했으나, 응답받은 객체에 문제가 있을 경우 [InvalidDataReceivedException]을 던집니다.
     * @return [Bot] 봇 정보를 반환합니다.
     */
    @Throws(RequestFailedException::class, InvalidDataReceivedException::class)
    fun getBotInfo(targetBotId: String): Bot =
        adapter.getBotInfo(targetBotId)

    /**
     * [Bot] 봇 정보를 비동기적으로 받아옵니다.
     * @param targetBotId 받아올 봇의 ID 입니다.
     * @param onSuccess 요청이 성공한 경우 호출됩니다.
     * @param onFailure 요청이 실패한 경우 호출됩니다. null인 경우 아무 동작도 하지 않습니다. 기본값은 null입니다.
     */
    @JvmOverloads
    fun getBotInfo(targetBotId: String, onSuccess: (Bot) -> Unit, onFailure: ((Throwable) -> Unit)? = null) =
        adapter.getBotInfo(targetBotId, onSuccess, onFailure)

    /**
     * 유저가 봇을 투표했는지 확인합니다.
     * @param userId 확인할 유저의 ID 입니다.
     * @throws [RequestFailedException] 요청이 실패한 경우 [RequestFailedException]을 던집니다.
     * @throws [InvalidDataReceivedException] 요청이 성공했으나, 응답받은 객체에 문제가 있을 경우 [InvalidDataReceivedException]을 던집니다.
     * @return [Bot] 봇 정보를 반환합니다.
     */
    @Throws(RequestFailedException::class, InvalidDataReceivedException::class)
    fun checkUserVote(userId: String): Voted =
        adapter.checkUserVote(userId)

    /**
     * 유저가 봇을 투표했는지 비동기적으로 확인합니다.
     * @param userId 확인할 유저의 ID 입니다.
     * @param onSuccess 요청이 성공한 경우 호출됩니다.
     * @param onFailure 요청이 실패한 경우 호출됩니다. null인 경우 아무 동작도 하지 않습니다. 기본값은 null입니다.
     */
    @JvmOverloads
    fun checkUserVote(userId: String, onSuccess: (Voted) -> Unit, onFailure: ((Throwable) -> Unit)? = null) =
        adapter.checkUserVote(userId, onSuccess, onFailure)

    /**
     * 봇 서버 수를 업데이트합니다.
     * @param servers 현재 이 봇이 참가한 서버의 수 입니다.
     * @throws [RequestFailedException] 요청이 실패한 경우 [RequestFailedException]을 던집니다.
     * @throws [InvalidDataReceivedException] 요청이 성공했으나, 응답받은 객체에 문제가 있을 경우 [InvalidDataReceivedException]을 던집니다.
     * @return [Bot] 봇 정보를 반환합니다.
     */
    @Throws(RequestFailedException::class, InvalidDataReceivedException::class)
    fun updateBotServers(servers: Int) =
        adapter.updateBotServers(servers)

    /**
     * 봇 서버 수를 비동기적으로 업데이트합니다.
     * @param servers 현재 이 봇이 참가한 서버의 수 입니다.
     * @param onSuccess 요청이 성공한 경우 호출됩니다.
     * @param onFailure 요청이 실패한 경우 호출됩니다. null인 경우 아무 동작도 하지 않습니다. 기본값은 null입니다.
     */
    @JvmOverloads
    fun updateBotServers(servers: Int, onSuccess: () -> Unit, onFailure: ((Throwable) -> Unit)? = null) =
        adapter.updateBotServers(servers, onSuccess, onFailure)

    /**
     * [User] 유저 정보를 받아옵니다. API v2에서만 지원합니다.
     * @param userId 받아올 유저의 ID 입니다.
     * @throws [RequestFailedException] 요청이 실패한 경우 [RequestFailedException]을 던집니다.
     * @throws [InvalidDataReceivedException] 요청이 성공했으나, 응답받은 객체에 문제가 있을 경우 [InvalidDataReceivedException]을 던집니다.
     * @return [User] 유저 정보를 반환합니다.
     */
    @Throws(RequestFailedException::class, InvalidDataReceivedException::class)
    fun getUserInfo(userId: String): User =
        adapter.getUserInfo(userId)

    /**
     * [User] 유저 정보를 비동기적으로 받아옵니다. API v2에서만 지원합니다.
     * @param userId 받아올 유저의 ID 입니다.
     * @param onSuccess 요청이 성공한 경우 호출됩니다.
     * @param onFailure 요청이 실패한 경우 호출됩니다. null인 경우 아무 동작도 하지 않습니다. 기본값은 null입니다.
     */
    @JvmOverloads
    fun getUserInfo(userId: String, onSuccess: (User) -> Unit, onFailure: ((Throwable) -> Unit)? = null) =
        adapter.getUserInfo(userId, onSuccess, onFailure)
}
