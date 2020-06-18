package schoolmanagement.java.main

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import schoolmanagement.java.dao.UsersDao
import schoolmanagement.java.utils.Directories
import java.io.IOException

class Launcher : Application() {
    var applicationContext: ApplicationContext? = null
    override fun start(primaryStage: Stage) {
        var root: Parent? = null
        try {
//            root = FXMLLoader.load<Parent>(javaClass.getResource(Directories.MAIN_FXML_DIR));
            root = FXMLLoader.load<Parent>(javaClass.getResource(Directories.LOGIN_FXML_DIR))
//            root = FXMLLoader.load<Parent>(javaClass.getResource(Directories.HOME_FXML_DIR))
        } catch (e: IOException) {
            println(e.message)
            e.printStackTrace()
        }
        val scene = Scene(root)
        scene.stylesheets.add(javaClass.getResource(Directories.JFOENIX_CSS_DIR).toExternalForm())
        primaryStage.title = "Students App"
        primaryStage.initStyle(StageStyle.UNDECORATED)
        primaryStage.isResizable = false
        primaryStage.scene = scene
        primaryStage.show()
        stage = primaryStage
    }

    @Throws(Exception::class)
    override fun init() {
        super.init()
        applicationContext = ClassPathXmlApplicationContext(Directories.CONFIG_XML)
        val dao = (applicationContext as ClassPathXmlApplicationContext).getBean("usersDao") as UsersDao
        val list = dao.allUsers
        for (e in list) {
            println(e)
        }
    }

    @Throws(Exception::class)
    override fun stop() {
        (applicationContext as ClassPathXmlApplicationContext?)!!.close()
        super.stop()
    }

    companion object {
        @JvmField
        var stage: Stage? = null

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Launcher::class.java)
        }
    }
}