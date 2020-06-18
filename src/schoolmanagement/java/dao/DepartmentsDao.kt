package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate

class DepartmentsDao {
    private var jdbcTemplate: JdbcTemplate? = null
    fun setJdbcTemplate(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }
}