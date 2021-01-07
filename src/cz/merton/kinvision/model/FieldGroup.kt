package cz.merton.kinvision.model

/**
 * Data model for a named group of fields that may appear in a return response
 * @author rvbiljouw
 */
data class FieldGroup(
    val name: String,
    val fields: Map<String, Field>
)

/**
 * Data model for a key-value pair that may appear in a [FieldGroup] response
 * @author rvbiljouw
 */
data class Field(
    val name: String,
    val value: String
)