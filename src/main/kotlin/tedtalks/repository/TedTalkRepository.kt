package tedtalks.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tedtalks.model.TedTalk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TedTalkRepository : JpaRepository<TedTalk, Long> {

    @Query("""
        SELECT t.author as author, SUM(t.views * 0.7 + t.likes * 0.3) AS influenceScore
        FROM TedTalk t
        GROUP BY t.author
        ORDER BY influenceScore DESC
    """)
    fun findTopInfluentialSpeakers(pageable: Pageable):Page<SpeakerInfluence>

    @Query(value = """
        WITH YearlyInfluence AS (
        SELECT 
            author, 
            EXTRACT(YEAR FROM date) AS year,
            SUM(CAST(views * 0.7 + likes * 0.3 AS NUMERIC)) AS influenceScore
        FROM 
            ted_talks
        GROUP BY 
            author, year
        )
        SELECT 
            y.year, 
            y.author, 
            y.influenceScore
        FROM 
            YearlyInfluence y
        JOIN (
            SELECT 
                year, 
                MAX(influenceScore) AS maxInfluenceScore
            FROM 
                YearlyInfluence
            GROUP BY 
                year
        ) max_scores
        ON y.year = max_scores.year AND y.influenceScore = max_scores.maxInfluenceScore
        ORDER BY y.year DESC;
""", nativeQuery = true)
    fun findTopSpeakerPerYear(): List<SpeakerInfluenceYearly>

    interface SpeakerInfluence {
        val author: String
        val influenceScore: Double
    }

    interface SpeakerInfluenceYearly {
        val author: String
        val year: Int
        val influenceScore: Double
    }
}