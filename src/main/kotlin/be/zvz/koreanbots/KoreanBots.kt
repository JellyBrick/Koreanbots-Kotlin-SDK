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
import be.zvz.koreanbots.dto.SearchResult
import be.zvz.koreanbots.dto.ServersUpdate
import be.zvz.koreanbots.dto.User
import be.zvz.koreanbots.dto.Voted
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.jackson.objectBody
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getOrElse

class KoreanBots @JvmOverloads constructor(
    private val token: String,
    private val mapper: ObjectMapper = JsonMapper()
        .registerKotlinModule()
        .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING),
    private val fuelManager: FuelManager = FuelManager()
) {
    init {
        fuelManager.basePath = KoreanBotsInfo.API_BASE_URL
        fuelManager.baseHeaders = mapOf(
            Headers.USER_AGENT to "Kotlin SDK(${KoreanBotsInfo.VERSION}, ${KoreanBotsInfo.GITHUB_URL})"
        )
    }

    /**
     * 봇 정보를 받아옵니다.
     * @param id 받아올 봇의 아이디
     * @throws [RequestFailedException] 요청이 실패한 경우
     * @return [Bot] 인스턴스
     */
    @Throws(RequestFailedException::class)
    fun getBotInfo(id: String): Bot = handleResponse(
        fuelManager
            .get("/v2/bots/$id")
            .responseObject<ResponseWrapper<Bot>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    /**
     * 봇 정보를 받아옵니다.
     * @param id 받아올 봇의 아이디
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun getBotInfo(id: String, onSuccess: (Bot) -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .get("/v2/bots/$id")
            .responseObject<ResponseWrapper<Bot>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    /**
     * 봇을 검색합니다.
     * @param query 검색어
     * @param page 페이지 번호(기본값: 1)
     * @throws [RequestFailedException] 요청이 실패한 경우
     * @return [SearchResult] 인스턴스
     */
    @JvmOverloads
    @Throws(RequestFailedException::class)
    fun searchBots(query: String, page: Int = 1): SearchResult = handleResponse(
        fuelManager
            .get("/v2/search/bots", listOf("query" to query, "page" to page))
            .responseObject<ResponseWrapper<SearchResult>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    /**
     * 봇을 검색합니다.
     * @param query 검색어
     * @param page 페이지 번호(기본값: 1)
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun searchBots(query: String, page: Int = 1, onSuccess: (SearchResult) -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .get("/v2/search/bots", listOf("query" to query, "page" to page))
            .responseObject<ResponseWrapper<SearchResult>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    /**
     * 하트 랭킹을 받아옵니다.
     * @param page 페이지 번호(기본값: 1)
     * @throws [RequestFailedException] 요청이 실패한 경우
     * @return [SearchResult] 인스턴스
     */
    @JvmOverloads
    @Throws(RequestFailedException::class)
    fun getHeartRanking(page: Int = 1): SearchResult = handleResponse(
        fuelManager
            .get("/v2/list/bots/votes", listOf("page" to page))
            .responseObject<ResponseWrapper<SearchResult>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    /**
     * 하트 랭킹을 받아옵니다.
     * @param page 페이지 번호(기본값: 1)
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun getHeartRanking(page: Int = 1, onSuccess: (SearchResult) -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .get("/v2/list/bots/votes", listOf("page" to page))
            .responseObject<ResponseWrapper<SearchResult>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    /**
     * 한디리에 새롭게 등록된 봇들을 받아옵니다.
     * @throws [RequestFailedException] 요청이 실패한 경우
     * @return [SearchResult] 인스턴스
     */
    @Throws(RequestFailedException::class)
    fun getNewBots(): SearchResult = handleResponse(
        fuelManager
            .get("/v2/list/bots/new")
            .responseObject<ResponseWrapper<SearchResult>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    /**
     * 한디리에 새롭게 등록된 봇들을 받아옵니다.
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun getNewBots(onSuccess: (SearchResult) -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .get("/v2/list/bots/new")
            .responseObject<ResponseWrapper<SearchResult>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    /**
     * 유저가 봇을 투표했는지 확인합니다.
     * @param id 봇의 아이디
     * @param userId 유저의 아이디
     * @throws [RequestFailedException] 요청이 실패한 경우
     * @return [Bot] 인스턴스
     */
    @Throws(RequestFailedException::class)
    fun checkUserVote(id: String, userId: String): Voted = handleResponse(
        fuelManager
            .get("/v2/bots/$id/vote", listOf("userID" to userId))
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ResponseWrapper<Voted>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    /**
     * 유저가 봇을 투표했는지 확인합니다.
     * @param id 봇의 아이디
     * @param userId 유저의 아이디
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun checkUserVote(id: String, userId: String, onSuccess: (Voted) -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .get("/v2/bots/$id/vote", listOf("userID" to userId))
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ResponseWrapper<Voted>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke(it ?: throw AssertionError("Request Success, but Data Doesn't Exist")) }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    /**
     * 봇 서버 수를 업데이트합니다.
     * @param id 봇의 아이디
     * @param servers 현재 서버 수
     * @throws [RequestFailedException] 요청이 실패한 경우
     */
    @Throws(RequestFailedException::class)
    fun updateBotServers(id: String, servers: Int) {
        handleResponse(
            fuelManager
                .post("/v2/bots/$id/stats")
                .header(Headers.AUTHORIZATION, token)
                .objectBody(ServersUpdate(servers), mapper = mapper)
                .responseObject<ResponseWrapper<Unit>>(mapper = mapper)
                .third
        )
    }

    /**
     * 봇 서버 수를 업데이트합니다.
     * @param id 봇의 아이디
     * @param servers 현재 서버 수
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun updateBotServers(id: String, servers: Int, onSuccess: () -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .post("/v2/bots/$id/stats")
            .header(Headers.AUTHORIZATION, token)
            .objectBody(ServersUpdate(servers), mapper = mapper)
            .responseObject<ResponseWrapper<Unit>>(mapper = mapper) { _, _, result ->
                runCatching { handleResponse(result) }
                    .mapCatching { onSuccess.invoke() }
                    .getOrElse { onFailure?.invoke(it) }
            }
    }

    /**
     * 유저 정보를 받아옵니다.
     * @param id 유저의 아이디
     * @throws [RequestFailedException] 요청이 실패한 경우
     * @return [User] 인스턴스
     */
    @Throws(RequestFailedException::class)
    fun getUserInfo(id: String): User = handleResponse(
        fuelManager
            .get("/v2/users/$id")
            .responseObject<ResponseWrapper<User>>(mapper = mapper)
            .third
    )
        ?: throw AssertionError("Request Success, but Data Doesn't Exist") // This may not occur, but in case of server api error

    /**
     * 유저 정보를 받아옵니다.
     * @param id 유저의 아이디
     * @param onSuccess 요청이 성공한 경우 호출될 콜백 함수
     * @param onFailure 요청이 실패한 경우 호출될 콜백 함수(기본값: null, 아무 동작도 하지 않음)
     */
    @JvmOverloads
    fun getUserInfo(id: String, onSuccess: (User) -> Unit, onFailure: ((Throwable) -> Unit)? = null) {
        fuelManager
            .get("/v2/users/$id")
            .header(Headers.AUTHORIZATION, token)
            .responseObject<ResponseWrapper<User>>(mapper = mapper) { _, _, result ->
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
}
