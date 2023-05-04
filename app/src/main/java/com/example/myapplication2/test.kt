package com.example.myapplication2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

lateinit var btnconn : Button
lateinit var txtnombre : EditText
lateinit var txtcoreo : EditText
lateinit var txtpass : EditText

var url = "https://marled-developments.000webhostapp.com/test/save.php"

lateinit var requestQueue: RequestQueue

/**
 * esta es la vista donde se ingresar datos encriptados a la base de datos
 */
class test() : ComponentActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestQueue = Volley.newRequestQueue(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        btnconn = findViewById(R.id.btnIngreso)
        txtnombre = findViewById(R.id.txtName)
        txtcoreo = findViewById(R.id.txtEmail)
        txtpass = findViewById(R.id.txtPass)

        btnconn.setOnClickListener (this)
    }

    override fun onClick(v: View?) {

        val id = v?.id

        if(id==R.id.btnIngreso){

            var name = txtnombre.getText().toString()
            var correo = txtcoreo.getText().toString()
            var pass = txtpass.getText().toString()

            addUser(name,correo,pass)

            val encriptador = Encriptar()
            val ciphertext = encriptador.encriptarname(name)
            println("Mensaje encriptado: ${ciphertext.toString()}")
            val plaintext = encriptador.desencriptarname(ciphertext)
            println("Mensaje desencriptado: $plaintext")

        }
    }
    /**
     * se guarda los usuario
     */
    private fun addUser(name: String, correo: String, pass: String) {
        val stringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener<String> { response ->
                        Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
                        txtnombre.text.clear()
                        txtcoreo.text.clear()
                        txtpass.text.clear()
                    },
                    Response.ErrorListener { error ->
                        println("nooo")
                    }
                ) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["nombre"] = name
                        params["email"] = correo
                        params["password"] = pass
                return params
            }
        }
        requestQueue.add(stringRequest)

    }

}