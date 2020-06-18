package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate
import schoolmanagement.java.models.Users

class UsersDao {
    private var jdbcTemplate: JdbcTemplate? = null

    fun setJdbcTemplate(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun userExist(username: String?, password: String?): Boolean {
        val query = "SELECT COUNT(USERNAME) FROM users where username = ? and password = ?"
        val exists = jdbcTemplate!!.queryForObject(query, Int::class.java, username, password)
        return exists == 1
    }

    fun userExist(username: String?): Boolean {
        val query = "SELECT COUNT(USERNAME) FROM users where username = ?"
        val exists = jdbcTemplate!!.queryForObject(query, Int::class.java, username)
        return exists == 1
    }

    val allUsers: List<Users>
        get() {
            val sql = "select * from users"
            return jdbcTemplate!!.query<List<Users>>(sql) { rs ->
                val list: MutableList<Users> = ArrayList()
                while (rs.next()) {
                    val users = Users()
                    users.id = rs.getString(1)
                    users.userName = rs.getString(2)
                    users.firstName = rs.getString(3)
                    users.lastName = rs.getString(4)
                    users.email = rs.getString(5)
                    users.mobileNumber = rs.getString(6)
                    users.role = rs.getString(7)
                    users.password = rs.getString(8)
                    list.add(users)
                }
                list
            }
        }

    fun saveUser(users: Users): Boolean {
        val query = "insert into users values(?,?,?,?,?,?,?,?)"
        return jdbcTemplate!!.execute(query) { ps ->
            ps.setString(1, users.id)
            ps.setString(2, users.userName)
            ps.setString(3, users.firstName)
            ps.setString(4, users.lastName)
            ps.setString(5, users.email)
            ps.setString(6, users.mobileNumber)
            ps.setString(7, users.role)
            ps.setString(8, users.password)
            ps.execute()
        }
    }

    fun getUser(username: String): List<Users> {
        val sql = "select * from users where `username` = '$username'"
        return jdbcTemplate!!.query<List<Users>>(sql) { rs ->
            val list: MutableList<Users> = ArrayList()
            while (rs.next()) {
                val users = Users()
                users.id = rs.getString(1)
                users.userName = rs.getString(2)
                users.firstName = rs.getString(3)
                users.lastName = rs.getString(4)
                users.email = rs.getString(5)
                users.mobileNumber = rs.getString(6)
                users.role = rs.getString(7)
                users.password = rs.getString(8)
                list.add(users)
            }
            list
        }
    }
}