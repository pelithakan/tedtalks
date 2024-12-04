package tedtalks.service.regular

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import tedtalks.model.TedTalk
import tedtalks.repository.TedTalkRepository

@Service
class TedTalkService(private val tedTalkRepository: TedTalkRepository) {

    @Transactional
    fun saveTedTalk(tedTalk: TedTalk): TedTalk = tedTalkRepository.save(tedTalk)

    fun getAllTedTalks(pageable: Pageable): Page<TedTalk> = tedTalkRepository.findAll(pageable)

    fun getTedTalkById(id: Long): TedTalk? = tedTalkRepository.findByIdOrNull(id)

    @Transactional
    fun deleteTedTalkById(id: Long) = tedTalkRepository.deleteById(id)
}