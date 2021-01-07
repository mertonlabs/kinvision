package cz.merton.kinvision.exception

/**
 * A generic exception that wraps any exceptions thrown by this library
 * @author rvbiljouw
 */
open class KInvisionException(message: String, exception: Exception? = null) : Exception(message, exception)

/**
 * An exception that's thrown when attempting to retrieve a member using an invalid ID.
 * @author rvbiljouw
 */
data class MemberNotFoundException(val id: Long) : KInvisionException("Member with ID $id not found.")