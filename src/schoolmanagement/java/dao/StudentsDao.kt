package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate

class StudentsDao {
    private var jdbcTemplate: JdbcTemplate? = null
    fun setJdbcTemplate(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }
}