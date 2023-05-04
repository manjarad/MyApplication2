package com.example.myapplication2

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException


class ConexionDb {
    internal var Puerto: String = "3306"
    internal var DB: String = "mysql"
    internal var User: String = "root"
    internal var Pas: String = "1234"
    internal var host: String ="localhost"


    fun getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            val url = "jdbc:mysql://$host:$Puerto/$DB"
            val conn = DriverManager.getConnection(url, User, Pas)
            println("Conexión establecida correctamente")
        } catch (e: SQLException) {
            println("Error al establecer la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("Error al cargar el driver: ${e.message}")
        }
    }
}