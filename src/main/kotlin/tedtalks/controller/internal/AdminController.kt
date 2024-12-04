package tedtalks.controller.internal

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tedtalks.service.DataImportService

@RestController
@RequestMapping("/admin")
class AdminController(
    private val dataImportService: DataImportService
) {

    @GetMapping("/import")
    suspend fun importData(): ResponseEntity<String> {
        dataImportService.initialLoadTedTalksFromCSV()
        return ResponseEntity.ok("Data import initiated")
    }
}