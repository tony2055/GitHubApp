package br.com.fiap.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GithubService::class.java)
            service.buscarUsuario(etNome.text.toString())
                .enqueue(object : Callback<Usuario>{
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(this@MainActivity,
                            t.message,
                            Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if(response.isSuccessful){
                            val usuario = response.body()
                            Picasso.get()
                                .load(usuario?.avatarURL)
                                .into(ivUsuario)

                            tvNomeUsuario.text = usuario?.name

                        } else{
                            Toast.makeText(this@MainActivity,
                                "Não foi possivel localizar o usuario",
                                Toast.LENGTH_LONG).show()

                        }
                    }

                })
        }
    }
}
