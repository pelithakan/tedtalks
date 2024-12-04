package tedtalks.controller.regular

import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import tedtalks.model.TedTalk
import tedtalks.service.regular.TedTalkService

@RestController
@RequestMapping("/api/ted-talks")
class TedTalkController(private val tedTalkService: TedTalkService) {

    @GetMapping
    fun getAllTedTalks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<List<TedTalk>> {
        val tedTalks = tedTalkService.getAllTedTalks(PageRequest.of(page, size)).toList()
        return ResponseEntity.ok(tedTalks)
    }

    @GetMapping("/{id}")
    fun getTedTalkById(@PathVariable id: Long): ResponseEntity<TedTalk> {
        val tedTalk = tedTalkService.getTedTalkById(id)
        return if (tedTalk != null) {
            ResponseEntity.ok(tedTalk)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @PostMapping
    fun createTedTalk(@RequestBody @Validated tedTalk: TedTalk): ResponseEntity<TedTalk> {
        val createdTedTalk = tedTalkService.saveTedTalk(tedTalk)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTedTalk)
    }

    @PutMapping("/{id}")
    fun updateTedTalk(@PathVariable id: Long, @RequestBody @Validated tedTalk: TedTalk): ResponseEntity<TedTalk> {
        val existingTedTalk = tedTalkService.getTedTalkById(id)
        return if (existingTedTalk != null) {
            val updatedTedTalk = tedTalk.copy(id = id)  // Updating the existing TedTalk
            tedTalkService.saveTedTalk(updatedTedTalk)
            ResponseEntity.ok(updatedTedTalk)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTedTalk(@PathVariable id: Long): ResponseEntity<Void> {
        return if (tedTalkService.getTedTalkById(id) != null) {
            tedTalkService.deleteTedTalkById(id)
            ResponseEntity.noContent().build() // Status 204: No Content
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}