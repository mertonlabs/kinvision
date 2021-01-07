package cz.merton.kinvision

import com.google.gson.JsonDeserializer
import cz.merton.kinvision.api.Members
import cz.merton.kinvision.exception.KInvisionException
import cz.merton.kinvision.model.CommunityInfo
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import java.time.ZonedDateTime

/**
 * Main class for the Invision API client
 * @author rvbiljouw
 */
class KInvision private constructor(
    private val key: String,
    val baseUrl: String,
    private val logLevel: LogLevel
) {
    private val client: HttpClient = HttpClient(Apache) {
        install(Auth) {
            basic {
                username = key
                password = ""
                sendWithoutRequest = true
            }
        }
        install(JsonFeature) {
            serializer = GsonSerializer {
                registerTypeAdapter(ZonedDateTime::class.java, JsonDeserializer { json, _, _ ->
                    ZonedDateTime.parse(json.asJsonPrimitive.asString)
                })
            }
        }
        install(Logging) {
            level = logLevel
        }
        BrowserUserAgent()
    }

    companion object {
        fun create(key: String, baseUrl: String, logLevel: LogLevel = LogLevel.NONE): KInvision {
            return KInvision(key, baseUrl, logLevel)
        }
    }

    val members = Members(this, client)

    /**
     * Retrieves Community information from the Invision API.
     */
    @Throws(KInvisionException::class)
    suspend fun getCommunityInfo(): CommunityInfo? {
        return try {
            client.get {
                url("$baseUrl/api/index.php?/core/hello")
            }
        } catch (e: Exception) {
            throw KInvisionException("Request failed due to client exception.", e)
        }
    }

}