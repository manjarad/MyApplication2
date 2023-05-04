package com.example.myapplication2

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

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

        initUI()
        btnconn.setOnClickListener (this)
    }

    fun initUI(){
        btnconn = findViewById(R.id.btnIngreso)
        txtnombre = findViewById(R.id.txtName)
        txtcoreo = findViewById(R.id.txtEmail)
        txtpass = findViewById(R.id.txtPass)
    }

    override fun onClick(v: View?) {

        val id = v?.id

        if(id==R.id.btnIngreso){

            var name = txtnombre.getText().toString()
            var correo = txtcoreo.getText().toString()
            var pass = txtpass.getText().toString()

            //createUser(name,correo,pass)
            val plaintext = name.toString().toByteArray()
            val keygen = KeyGenerator.getInstance("AES")
            keygen.init(256)
            val key: SecretKey = keygen.generateKey()

            // Cifrar datos en modo CBC con relleno PKCS5
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val ciphertext: ByteArray = cipher.doFinal(plaintext)
            val iv: ByteArray = cipher.iv

            Log.d("cifrado","${ciphertext.toString()}")

            val cipher1 = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher1.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
            val plaintext2: ByteArray = cipher1.doFinal(ciphertext)
            val plaintextString = String(plaintext2, Charsets.UTF_8)
            Log.d("TAG", plaintextString)


        }
    }
    /**
     * se guarda los usuario
     */
    private fun createUser(name: String, correo: String, pass: String) {
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