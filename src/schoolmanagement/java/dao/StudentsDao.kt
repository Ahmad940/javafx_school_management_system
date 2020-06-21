package schoolmanagement.java.dao

import org.springframework.jdbc.core.JdbcTemplate
import schoolmanagement.java.models.Students

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

    val allStudents: List<Students>
        get() {
            val sql = "select * from students"
            return jdbcTemplate!!.query<List<Students>>(sql) { rs ->
                val list: MutableList<Students> = ArrayList()
                while (rs.next()) {
                    val students = Students()
                    students?.id = rs.getString(1)
                    students?.firstName = rs.getString(2)
                    students?.lastName = rs.getString(3)
                    students?.mobileNumber = rs.getString(4)
                    students?.email = rs.getString(5)
                    students?.location = rs.getString(6)
                    students?.gender = rs.getString(7)
                    students?.registrationDate = rs.getString(8)
                    students?.level = rs.getString(9)
                    students?.department = rs.getString(10)
                    students?.courseName = rs.getString(11)
                    students?.amount = rs.getString(12)
                    students?.balance = rs.getString(13)
                    list.add(students)
                }
                list
            }
        }

    fun saveStudent(student: Students): Boolean {
        val query = "insert into students values(?,?,?,?,?,?,?,?,?,?,?,?,?)"
        return jdbcTemplate!!.execute(query) { ps ->
            ps?.setString(1, student.id)
            ps?.setString(2, student.firstName)
            ps?.setString(3, student.lastName)
            ps?.setString(4, student.mobileNumber)
            ps?.setString(5, student.email)
            ps?.setString(6, student.location)
            ps?.setString(7, student.gender)
            ps?.setString(8, student.registrationDate)
            ps?.setString(9, student.level)
            ps?.setString(10, student.department)
            ps?.setString(11, student.courseName)
            ps?.setString(12, student.amount)
            ps?.setString(13, student.balance)
            ps.execute()
        }
    }

    fun getStudent(id: String): List<Students> {
        val sql = "select * from students where `id` = '$id'"
        return jdbcTemplate!!.query<List<Students>>(sql) { rs ->
            val list: MutableList<Students> = ArrayList()
            while (rs.next()) {
                val students = Students()
                students.id = rs.getString(1)
                students.firstName = rs.getString(2)
                students.lastName = rs.getString(3)
                students.mobileNumber = rs.getString(4)
                students.email = rs.getString(5)
                students.location = rs.getString(6)
                students.gender = rs.getString(7)
                students.registrationDate = rs.getString(8)
                students.level = rs.getString(9)
                students.department = rs.getString(10)
                students.courseName = rs.getString(11)
                students.amount = rs.getString(12)
                students.balance = rs.getString(13)
                list.add(students)
            }
            list
        }
    }

    fun deleteStudent(id: String): Int {
        val query = "delete from students where id = '$id' "
        return jdbcTemplate!!.update(query)
    }

}