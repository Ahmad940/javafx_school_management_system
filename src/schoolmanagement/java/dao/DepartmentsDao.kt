package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate
import schoolmanagement.java.models.Departments

class DepartmentsDao {
    private var jdbcTemplate: JdbcTemplate? = null
    fun setJdbcTemplate(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }

    val departments: List<Departments>
        get() {
            val sql = "select * from departments"
            return jdbcTemplate!!.query<List<Departments>>(sql) { rs ->
                val list: MutableList<Departments> = ArrayList()
                while (rs.next()) {
                    val departments = Departments()
                    departments.id = rs.getString(1)
                    departments.department = rs.getString(2)
                    list.add(departments)
                }
                list
            }
        }

    fun deleteDepartment(name: String): Int {
        val query = "delete from departments where department = '$name' "
        return jdbcTemplate!!.update(query)
    }

    fun saveDepartment(deptName: String): Boolean? {
        val query = "insert into departments values(?,?)"
        return jdbcTemplate!!.execute(query) { ps ->
            ps.setString(1, null)
            ps.setString(2, deptName)
            ps.execute()
        }
    }

}