import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class MySQLConnector {
    private val URL = "jdbc:mysql://192.168.1.2:3307/db_tagakaulo"
    private val USERNAME = "root"
    private val PASSWORD = ""

    private var connection: Connection? = null
    fun connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            connection?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun executeQuery(query: String): ResultSet {
        val statement: Statement = connection!!.createStatement()
        return statement.executeQuery(query)
    }
}
