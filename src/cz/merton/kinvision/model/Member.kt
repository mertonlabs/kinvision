package cz.merton.kinvision.model

import java.time.ZonedDateTime

/**
 * Data model representing an Invision Member.
 * @author rvbiljouw
 */
data class Member(
    val id: Long,
    val name: String,
    val title: String,
    val timezone: String,
    val formattedName: String,
    val ipAddress: String,
    val primaryGroup: Group,
    val secondaryGroups: List<Group>,
    val email: String,
    val joined: ZonedDateTime,
    val registrationIpAddress: String,
    val warningPoints: Int,
    val reputationPoints: Int,
    val photoUrl: String,
    val photoUrlIsDefault: Boolean,
    val coverPhotoUrl: String,
    val profileUrl: String,
    val validating: Boolean,
    val posts: Int,
    val lastActivity: ZonedDateTime?,
    val lastVisit: ZonedDateTime?,
    val lastPost: ZonedDateTime?,
    val profileViews: Int,
    val birthday: String,
    val customFields: Map<String, FieldGroup>
)