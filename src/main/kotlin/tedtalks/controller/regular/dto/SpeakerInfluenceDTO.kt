package tedtalks.controller.regular.dto

data class SpeakerInfluenceDTO(
    val author: String,
    val influenceScore: Double
)

data class SpeakerInfluenceYearlyDTO(
    val author: String,
    val year: Int,
    val influenceScore: Double
)
