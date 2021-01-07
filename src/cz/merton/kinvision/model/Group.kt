package cz.merton.kinvision.model

/**
 * Data model representing an Invision Group
 * @author rvbiljouw
 */
data class Group(
    val id: Long,
    val name: String,
    val formattedName: String
)
