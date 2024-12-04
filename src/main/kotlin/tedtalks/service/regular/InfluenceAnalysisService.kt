package tedtalks.service.regular

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import tedtalks.controller.regular.dto.SpeakerInfluenceDTO
import tedtalks.controller.regular.dto.SpeakerInfluenceYearlyDTO
import tedtalks.repository.TedTalkRepository

@Service
class InfluenceAnalysisService(private val tedTalkRepository: TedTalkRepository) {

    fun getTopInfluentialSpeakers(pageable: Pageable): Page<SpeakerInfluenceDTO> {
        val repositoryResult = tedTalkRepository.findTopInfluentialSpeakers(pageable)
        return repositoryResult.map { SpeakerInfluenceDTO(it.author, it.influenceScore) }
    }

    fun getTopSpeakerPerYear(): List<SpeakerInfluenceYearlyDTO> {
        val repositoryResult = tedTalkRepository.findTopSpeakerPerYear()
        return repositoryResult.map { SpeakerInfluenceYearlyDTO(it.author, it.year, it.influenceScore) }
    }
}
