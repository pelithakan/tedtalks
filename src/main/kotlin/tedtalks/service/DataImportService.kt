package tedtalks.service

import com.opencsv.CSVReader
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tedtalks.model.TedTalk
import tedtalks.repository.TedTalkRepository
import java.io.InputStreamReader

@Service
class DataImportService(private val tedTalkRepository: TedTalkRepository) {

    @OptIn(DelicateCoroutinesApi::class)
    @Transactional
    suspend fun initialLoadTedTalksFromCSV() {
        GlobalScope.launch(Dispatchers.IO) {
            val resource = ClassPathResource("data.csv")
            CSVReader(InputStreamReader(resource.inputStream))
                .use { reader -> //using 'use' to ensure the resource is closed properly
                    reader.readNext() // Skip header
                    reader.asSequence().asFlow() //streaming data - Reads and processes one row at a time.
                        .mapNotNull { TedTalk.fromCSVRow(it) }
                        .collect { tedTalkRepository.save(it) }
                }
        }
    }
}