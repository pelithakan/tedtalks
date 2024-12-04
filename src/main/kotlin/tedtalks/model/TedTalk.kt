package tedtalks.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.LocalDate
import java.time.Month

@Entity
@Table(name = "ted_talks")
data class TedTalk(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val title: String,
    val author: String,
    val date: LocalDate,
    val views: Long = 0,
    val likes: Long = 0,
    val link: String? = null,

    @CreationTimestamp
    val creationDateTime: Instant? = null,

    @UpdateTimestamp
    val lastModifiedDate: Instant? = null,

    @Version
    val version: Int = 0
) {
    companion object {
        fun fromCSVRow(row: Array<String>): TedTalk? {
            if (!isValidRow(row)) return null

            val title = row[0].trim()
            val author = row[1].trim()
            val date = parseDate(row[2]) ?: return null
            val views = row[3].toLongOrNull() ?: 0L
            val likes = row[4].toLongOrNull() ?: 0L
            val link = row.getOrNull(5)?.takeIf { it.isNotBlank() }

            return TedTalk(
                title = title,
                author = author,
                date = date,
                views = views,
                likes = likes,
                link = link
            )
        }

        private fun isValidRow(row: Array<String>): Boolean {
            return row.size >= 5 && row[0].isNotBlank() && row[1].isNotBlank()
        }

        private fun parseDate(dateString: String): LocalDate? {
            return try {
                val (monthName, yearString) = dateString.trim().split(" ")
                val year = yearString.toIntOrNull() ?: return null
                val normalizedMonthName = monthName.uppercase().replace("İ", "I")
                val month = Month.valueOf(normalizedMonthName)
                LocalDate.of(year, month, 1)
            } catch (e: Exception) {
                null
            }
        }
    }
}
