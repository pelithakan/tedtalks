package tedtalks.controller.regular

import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tedtalks.controller.regular.dto.SpeakerInfluenceDTO
import tedtalks.controller.regular.dto.SpeakerInfluenceYearlyDTO
import tedtalks.service.regular.InfluenceAnalysisService

@RestController
@RequestMapping("/api/ted-talks/analyse")
class AnalyseController(
    private val influenceAnalysisService: InfluenceAnalysisService
) {

    @GetMapping("/top-influential-speakers")
    fun getTopInfluences(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): List<SpeakerInfluenceDTO> =
        influenceAnalysisService.getTopInfluentialSpeakers(PageRequest.of(page, size)).toList()

    @GetMapping("/top-speaker-per-year")
    fun getTopSpeakerPerYear(): List<SpeakerInfluenceYearlyDTO> =
        influenceAnalysisService.getTopSpeakerPerYear()
}
