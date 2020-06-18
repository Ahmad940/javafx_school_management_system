package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate

class StudentsDao {

    private var jdbcTemplate: JdbcTemplate? = null

    fun setJdbcTemplate(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun totalReg(): Int? {
        val sql = "SELECT COUNT(*) from Students"
        val result = jdbcTemplate!!.queryForObject(sql, Int::class.java)
        return result.toInt()
    }

    fun totalMales(): Int? {
        val sql = "SELECT COUNT(*) from Students where `gender` = 'male'"
        val result = jdbcTemplate!!.queryForObject(sql, Int::class.java)
        return result.toInt()
    }

    fun totalFemales(): Int? {
        val sql = "SELECT COUNT(*) from Students where `gender` = 'female'"
        val result = jdbcTemplate!!.queryForObject(sql, Int::class.java)
        return result.toInt()
    }

}