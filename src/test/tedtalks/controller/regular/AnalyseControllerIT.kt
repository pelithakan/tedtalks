package tedtalks.controller.regular

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tedtalks.model.TedTalk
import tedtalks.repository.TedTalkRepository
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class AnalyseControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var tedTalkRepository: TedTalkRepository

    @BeforeEach
    fun setUp() {
        tedTalkRepository.deleteAll()

        val tedTalk1 = TedTalk(
            title = "Ted Talk 1",
            author = "Author 1",
            views = 50,
            likes = 30,
            date = LocalDate.of(2024, 12, 1)
        )
        val tedTalk2 =
            TedTalk(title = "Ted Talk 2", author = "Author 2", views = 30, likes = 30, date = LocalDate.of(2024, 12, 1))

        val tedTalk3 =
            TedTalk(title = "Ted Talk 3", author = "Author 3", views = 20, likes = 30, date = LocalDate.of(2023, 12, 1))

        tedTalkRepository.saveAll(listOf(tedTalk1, tedTalk2, tedTalk3))
    }

    @Test
    fun testGetTopInfluences() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/ted-talks/analyse/top-influential-speakers")
                .param("page", "0")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].author").value("Author 3"))
    }

    @Test
    fun testGetTopSpeakerPerYear() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/ted-talks/analyse/top-speaker-per-year")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2024"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 3"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value("2023"))
    }
}
