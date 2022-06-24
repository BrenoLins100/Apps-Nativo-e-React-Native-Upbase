package com.example.buscacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var logradouro: TextView
    private lateinit var bairro: TextView
    private lateinit var localidade: TextView
    private lateinit var uf: TextView
    private lateinit var ddd: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logradouro = findViewById<TextView>(R.id.txt_logradouro)
        bairro = findViewById<TextView>(R.id.txt_bairro)
        localidade = findViewById<TextView>(R.id.txt_localidade)
        uf = findViewById<TextView>(R.id.txt_uf)
        ddd = findViewById<TextView>(R.id.txt_ddd)

        val botaoBuscar = findViewById<Button>(R.id.btn_buscar)
        botaoBuscar.setOnClickListener {
            buscaCep()
        }
    }
        private fun buscaCep(){
            val cep = findViewById<EditText>(R.id.txt_cep)
            val valor = cep.text.toString()

            if(valor.isEmpty())
                return

            Thread{
                val url = URL("https://viacep.com.br/ws/${valor}/json/")
                val conn = url.openConnection() as HttpsURLConnection

                try{
                    val dados = conn.inputStream.bufferedReader().readText()

                    val objeto = JSONObject(dados)

                    val getLogradouro = objeto.getString("logradouro")
                    val getLocalidade = objeto.getString("localidade")
                    val getBairro = objeto.getString("bairro")
                    val getUf = objeto.getString("uf")
                    val getDdd = objeto.getString("ddd")

                    runOnUiThread {

                        logradouro.text = getLogradouro
                        localidade.text = getLocalidade
                        bairro.text = getBairro
                        uf.text = getUf
                        ddd.text = getDdd
                    }

                } finally{
                    conn.disconnect()
                }

            }.start()

        }

    }
