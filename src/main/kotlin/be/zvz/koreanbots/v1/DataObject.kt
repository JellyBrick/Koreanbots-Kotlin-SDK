/*
 * This code has been referred to the code in DV8FromTheWorld/JDA.
 */
package be.zvz.koreanbots.v1

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.full.isSuperclassOf

@JvmInline
internal value class DataObject private constructor(private val data: Map<String, Any?>) {

    internal constructor(json: String, mapper: JsonMapper) : this(
        mapper.readValue<Map<String, Any?>>(json)
    )

    fun hasKey(key: String) = data.containsKey(key)
    fun isNull(key: String) = data.getOrElse(key) { throw IllegalArgumentException("Unknown key $key") } == null

    fun getObject(key: String): DataObject? =
        get<Map<String, Any?>>(key, null, null)?.let { DataObject(it) }

    fun <T> getList(key: String): List<T>? =
        get(key, null, null)

    fun getString(key: String): String? =
        get(key, String::toString, Number::toString)

    fun getBoolean(key: String): Boolean? =
        get(key, String::toBoolean, null)

    fun getInt(key: String): Int? =
        get(key, String::toInt, Number::toInt)

    private inline fun <reified T : Any> get(
        key: String,
        noinline stringParse: ((String) -> T)?,
        noinline numberParse: ((Number) -> T)?
    ): T? =
        get(key, T::class, stringParse, numberParse)

    private fun <T : Any> get(
        key: String,
        type: KClass<T>,
        stringParse: ((String) -> T)?,
        numberParse: ((Number) -> T)?
    ): T? {
        val value = data.getValue(key) ?: return null
        if (type.isSuperclassOf(value::class)) return type.cast(value)

        if (value is Number && numberParse != null) return numberParse(value)
        if (value is String && stringParse != null) return stringParse(value)

        throw RuntimeException("Cannot cast [$key]-${value::class.simpleName} into ${type.simpleName}:")
    }
}
