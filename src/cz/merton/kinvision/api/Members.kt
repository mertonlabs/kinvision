package cz.merton.kinvision.api

import cz.merton.kinvision.KInvision
import cz.merton.kinvision.exception.KInvisionException
import cz.merton.kinvision.exception.MemberNotFoundException
import cz.merton.kinvision.model.Member
import cz.merton.kinvision.model.ResponsePage
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.FormUrlEncoded
import io.ktor.http.ContentType.Application.Json

/**
 * Provides an interface to interact with the Members endpoint of the Invision REST API
 * For more information see: https://invisioncommunity.com/developers/rest-api?endpoint=core/members/GETindex
 * @author rvbiljouw
 */
class Members(private val instance: KInvision, private val client: HttpClient) {

    /**
     * Retrieves a [Member][cz.merton.kinvision.model.Member] by [id]
     * @return a matching member
     */
    @Throws(KInvisionException::class)
    suspend fun getById(id: Long): Member? {
        return try {
            client.get<Member> {
                url("${instance.baseUrl}/api/index.php?/core/members/$id")
            }
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                throw MemberNotFoundException(id)
            } else {
                throw KInvisionException("Unknown error occurred.", e)
            }
        }
    }

    /**
     * Data model for a [Member][cz.merton.kinvision.model.Member] search request
     */
    data class SearchRequest(
        val sortBy: String? = null,
        val sortDir: String = "asc",
        val name: String? = null,
        val email: String? = null,
        val group: Long? = null,
        val activityAfter: Long? = null,
        val activityBefore: Long? = null,
        val page: Int = 1,
        val perPage: Int = 25
    )

    /**
     * Searches the Member database using the parameters provided in [request]
     * @return a list of matching members
     */
    @Throws(KInvisionException::class)
    suspend fun search(request: SearchRequest = SearchRequest()): ResponsePage<Member> {
        return try {
            client.get {
                url("${instance.baseUrl}/api/index.php?/core/members")
                parameter("sortBy", request.sortBy)
                parameter("sortDir", request.sortDir)
                parameter("name", request.name)
                parameter("email", request.email)
                parameter("group", request.group)
                parameter("activity_after", request.activityAfter)
                parameter("activity_before", request.activityBefore)
                parameter("page", request.page)
                parameter("perPage", request.perPage)
            }
        } catch (e: ClientRequestException) {
            throw KInvisionException("Unknown error occurred.", e)
        }
    }

    /**
     * Data model for creating and updating [Member][cz.merton.kinvision.model.Member] entries
     */
    data class CRUDRequest(
        val name: String,
        val email: String,
        val password: String? = null,
        val group: Long,
        val registrationIpAddress: String? = null,
        val secondaryGroups: List<Long>? = null,
        val customFields: Map<String, Any>? = null,
        val validated: Boolean? = null,
        val rawProperties: Map<String, Any>? = null
    ) {
        fun toFormData(): FormDataContent {
            return FormDataContent(Parameters.build {
                append("name", name)
                append("email", email)
                append("group", group.toString())
                password?.let { append("password", it) }
                validated?.let { append("validated", if (validated) "1" else "0") }
                registrationIpAddress?.let { append("registrationIpAddress", it) }

                secondaryGroups?.forEach {
                    append("secondaryGroups[]", it.toString())
                }

                customFields?.entries?.forEach {
                    append("customFields[]", "${it.key}=${it.value}")
                }

                rawProperties?.entries?.forEach {
                    append("rawProperties[]", "${it.key}=${it.value}")
                }
            })
        }
    }

    /**
     * Creates a new [Member][cz.merton.kinvision.model.Member] using the details provided in [request]
     * @return the created member
     */
    suspend fun create(request: CRUDRequest): Member {
        return try {
            client.submitForm {
                url("${instance.baseUrl}/api/index.php?/core/members")
                body = request.toFormData()
            }
        } catch (e: ClientRequestException) {
            throw KInvisionException("Unknown error occurred", e)
        }
    }

    /**
     * Updates an existing [Member][cz.merton.kinvision.model.Member] by [id] using the details provided in [request]
     */
    suspend fun update(id: Long, request: CRUDRequest): Member {
        return try {
            client.submitForm {
                url("${instance.baseUrl}/api/index.php?/core/members/$id")
                body = request.toFormData()
            }
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                throw MemberNotFoundException(id)
            } else {
                throw KInvisionException("Unknown error occurred.", e)
            }
        }
    }

}
