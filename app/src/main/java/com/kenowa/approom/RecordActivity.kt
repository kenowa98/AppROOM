package com.kenowa.approom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kenowa.approom.model.Registro
import com.kenowa.approom.model.RegistroDAO
import kotlinx.android.synthetic.main.activity_record.*
import java.sql.Types

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // Registrar la información una vez se oprima el botón de guardar
        bt_registro.setOnClickListener { solicitudRegistro() }
    }

    private fun solicitudRegistro() {
        val correo = et_correo.text.toString()
        val celular = et_celular.text.toString()
        val clave = et_clave.text.toString()
        val claveAgain = et_claveAgain.text.toString()
        val genero = generoUser()

        // Verificar que todos los campos hallan sido llenados
        datosCompletos(correo, celular, clave, claveAgain, genero)
    }

    private fun generoUser(): String {
        return if (rbtn_masculino.isChecked) {
            "Masculino"
        } else {
            "Femenino"
        }
    }

    private fun datosCompletos(
        correo: String,
        celular: String,
        clave: String,
        claveAgain: String,
        genero: String
    ) {
        if (correo.isEmpty() || celular.isEmpty() || clave.isEmpty() || claveAgain.isEmpty()) {
            Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_SHORT).show()
        } else {
            // Verificar que la clave cumpla unos requisitos mínimos
            claveRequisitos(correo, celular, clave, claveAgain, genero)
        }
    }

    private fun claveRequisitos(
        correo: String,
        celular: String,
        clave: String,
        claveAgain: String,
        genero: String
    ) {
        if (clave.length >= 6) {
            // Verificar que las claves coincidan
            claveCorrecta(correo, celular, clave, claveAgain, genero)
        } else {
            Toast.makeText(
                this,
                "Contraseñas de 6 caracteres mínimo", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun claveCorrecta(
        correo: String,
        celular: String,
        clave: String,
        claveAgain: String,
        genero: String
    ) {
        if (clave == claveAgain) {
            val user = Registro(Types.NULL, correo, clave, celular, genero)
            val registroDAO: RegistroDAO = AppROOM.database2.RegistroDAO()
            registroDAO.crearRegistro(user)
            onBackPressed()
        } else {
            Toast.makeText(
                this,
                "Las contraseñas no son iguales", Toast.LENGTH_SHORT
            ).show()
        }
    }
}