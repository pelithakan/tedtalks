package tedtalks.controller.internal

import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tedtalks.service.DataImportService

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val dataImportService: DataImportService = mockk()

    @BeforeEach
    fun setUp() {
        coEvery { dataImportService.initialLoadTedTalksFromCSV() } returns Unit
    }

    @Test
    fun testImportData() {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/import")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}
