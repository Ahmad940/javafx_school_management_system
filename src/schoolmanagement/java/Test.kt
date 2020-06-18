package schoolmanagement.java

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import schoolmanagement.java.dao.StudentsDao
import schoolmanagement.java.dao.UsersDao
import schoolmanagement.java.models.Users
import schoolmanagement.java.utils.Directories

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val applicationContext: ApplicationContext = ClassPathXmlApplicationContext(Directories.CONFIG_XML)
        val dao = applicationContext.getBean("usersDao") as UsersDao
        val studentsDao = applicationContext.getBean("studentsDao") as StudentsDao

//        println(studentsDao.totalReg())
        //        Users users = new Users();
//        users.setUserName("a");
//        users.setFirstName("s");
//        users.setLastName("s");
//        users.setEmail("s");
//        users.setMobileNumber("23sdsa");
//        users.setRole("s");
//        users.setPassword("kjdshfnejdkshfn kl");
//        System.out.println(dao.saveUser(users));
//        println(dao.userExist("admin"))

        val user: List<Users> = dao.getUser("admin")
        println(user)
    }
}