package com.kenowa.approom

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kenowa.approom.model.Registro
import com.kenowa.approom.model.RegistroDAO
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("SENSELESS_COMPARISON")
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Ir a registro
        bt_registro.setOnClickListener { goToRecord() }

        // Ir a la actividad principal
        bt_login.setOnClickListener { solicitudMain() }
    }

    private fun solicitudMain() {
        val correo = et_email.text.toString()
        val clave = et_clave.text.toString()

        // Verificar que todos los campos hallan sido llenados
        datosCompletos(correo, clave)
    }

    private fun datosCompletos(correo: String, clave: String) {
        if (correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Hay campos vacíos", Toast.LENGTH_SHORT).show()
        } else {
            // Buscar la información dada en el registro de usuarios
            registroDeUsuarios(correo, clave)
        }
    }

    private fun registroDeUsuarios(correo: String, clave: String) {
        val registroDAO: RegistroDAO = AppROOM.database2.RegistroDAO()
        val deudor = registroDAO.buscarRegistro(correo, clave)

        // Confirmar que se ha encontrado el usuario
        verificarUsuario(deudor)
    }

    private fun verificarUsuario(deudor: Registro) {
        if (deudor != null) {
            goToMain()
        } else {
            Toast.makeText(
                this,
                "Correo y/o contraseña incorrecta", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToRecord() {
        startActivity(Intent(this, RecordActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}