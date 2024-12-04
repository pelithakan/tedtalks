package tedtalks.controller.regular

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tedtalks.model.TedTalk
import tedtalks.repository.TedTalkRepository
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class TedTalkControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var tedTalkRepository: TedTalkRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @AfterEach
    fun tearDown() {
        tedTalkRepository.deleteAll()
    }

    @Test
    fun testCreateTedTalk() {
        val tedTalkJson = mapOf(
            "title" to "New TedTalk",
            "author" to "New Author",
            "date" to LocalDate.of(2024, 12, 4).toString(),
            "views" to 1000,
            "likes" to 100
        )

        mockMvc.perform(MockMvcRequestBuilders.post("/api/ted-talks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tedTalkJson)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.title").value("New TedTalk"))
            .andExpect(jsonPath("$.author").value("New Author"))
    }

    @Test
    fun testGetTedTalkById() {
        val savedTedTalk = tedTalkRepository.save(
            TedTalk(
                title = "Test Title",
                author = "Test Author",
                date = LocalDate.now(),
                views = 500,
                likes = 50
            )
        )

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ted-talks/${savedTedTalk.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.author").value("Test Author"))
    }

    @Test
    fun testUpdateTedTalk() {
        val savedTedTalk = tedTalkRepository.save(
            TedTalk(
                title = "Old Title",
                author = "Old Author",
                date = LocalDate.now(),
                views = 300,
                likes = 30
            )
        )

        val updatedTedTalkJson = mapOf(
            "title" to "Updated Title",
            "author" to "Updated Author",
            "date" to LocalDate.of(2024, 12, 4).toString(),
            "views" to 1000,
            "likes" to 100
        )

        mockMvc.perform(MockMvcRequestBuilders.put("/api/ted-talks/${savedTedTalk.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedTedTalkJson)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Updated Title"))
            .andExpect(jsonPath("$.author").value("Updated Author"))
    }

    @Test
    fun testDeleteTedTalk() {
        val savedTedTalk = tedTalkRepository.save(
            TedTalk(
                title = "To Be Deleted",
                author = "To Be Deleted",
                date = LocalDate.now(),
                views = 200,
                likes = 20
            )
        )

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ted-talks/${savedTedTalk.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ted-talks/${savedTedTalk.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }
}