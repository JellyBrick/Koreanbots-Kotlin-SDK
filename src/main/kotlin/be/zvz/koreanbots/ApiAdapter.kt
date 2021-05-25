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

internal interface ApiAdapter {
    // Common Feature
    fun getBotInfo(targetBotId: String): Bot
    fun getBotInfo(targetBotId: String, onSuccess: (Bot) -> Unit, onFailure: ((Throwable) -> Unit)?)

    fun checkUserVote(userId: String): Voted
    fun checkUserVote(userId: String, onSuccess: (Voted) -> Unit, onFailure: ((Throwable) -> Unit)?)

    fun updateBotServers(servers: Int)
    fun updateBotServers(servers: Int, onSuccess: () -> Unit, onFailure: ((Throwable) -> Unit)?)

    // API v1 Only
    fun getBotList(page: Int): List<Bot> {
        throw UnsupportedOperationException("Only Supported on API v1")
    }
    fun getBotList(page: Int, onSuccess: (List<Bot>) -> Unit, onFailure: ((Throwable) -> Unit)?) {
        throw UnsupportedOperationException("Only Supported on API v1")
    }

    // API v2 Only
    fun getUserInfo(userId: String): User {
        throw UnsupportedOperationException("Only Supported on API v2")
    }
    fun getUserInfo(userId: String, onSuccess: (User) -> Unit, onFailure: ((Throwable) -> Unit)?) {
        throw UnsupportedOperationException("Only Supported on API v2")
    }
}
