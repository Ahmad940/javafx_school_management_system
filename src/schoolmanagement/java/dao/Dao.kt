package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate

class Dao {
    private var jdbcTemplate: JdbcTemplate? = null
    fun setJdbcTemplate(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }
}