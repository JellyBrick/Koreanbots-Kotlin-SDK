/*
 * Copyright 2020 JellyBrick. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.zvz.koreanbots

import be.zvz.koreanbots.type.BotList
import be.zvz.koreanbots.type.RequestException
import be.zvz.koreanbots.type.mybots.response.Voted
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.objectBody
import com.github.kittinunf.fuel.jackson.responseObject
import be.zvz.koreanbots.type.mybots.request.Servers as ServersRequest
import be.zvz.koreanbots.type.mybots.response.Servers as ServersResponse

class KoreanBots(val token: String) {
    init {
        FuelManager.instance.basePath = "https://api.koreanbots.dev"
    }

    fun getBotList(page: Int): BotList {
        lateinit var botList: BotList

        "/bots/get"
                .httpGet(listOf(Pair("page", page)))
                .responseObject<BotList> { _, _, result ->
                    botList = result.get()

                    botList.message?.let {
                        throw RequestException(botList.code, it)
                    }
                }

        return botList
    }

    fun updateServerCount(serverCount: Int): ServersResponse {
        lateinit var servers: ServersResponse

        "/bots/servers"
                .httpPost()
                .header(Pair("token", token))
                .objectBody(ServersRequest(serverCount))
                .responseObject<ServersResponse> { _, _, result ->
                    servers = result.get()

                    servers.message?.let {
                        throw RequestException(servers.code, it)
                    }
                }

        return servers
    }

    fun checkVoted(userId: Int): Voted {
        lateinit var voted: Voted

        "/bots/voted/${userId}"
                .httpGet()
                .responseObject<Voted> { _, _, result ->
                    voted = result.get()

                    voted.message?.let {
                        throw RequestException(voted.code, it)
                    }
                }

        return voted
    }
}
