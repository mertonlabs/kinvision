package cz.merton.kinvision.model

/**
 * A generic wrapper around the list-response format provided by Invision
 * @author rvbiljouw
 */
data class ResponsePage<T>(
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: List<T>
)