package com.cursosant.android.userssp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.android.userssp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), IOnClickListener {

    private lateinit var userAdapter: UserAdapter //instancia del adaptador
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager //para que sea de una sola columna

    private lateinit var binding: ActivityMainBinding //vincular la vista del RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time), true)
        Log.i("SP", "${getString(R.string.sp_first_time)} = $isFirstTime")

        if(isFirstTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    val username =
                        dialogView.findViewById<TextInputEditText>(R.id.etUsername).text.toString()
                    with(preferences.edit()){
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                            .apply()
                    }
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    //preferences.edit().putBoolean(getString(R.string.sp_first_time), false).commit()
                }
                //.setNegativeButton("Cancelar", null)
                .setNeutralButton(R.string.dialog_neutral, null)
                .show()
        } else {
            val username = preferences.getString(getString(R.string.sp_username), getString(R.string.hint_username))
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
        }


        //userAdapter = UserAdapter(mutableListOf())//luego cambiaremos
        userAdapter = UserAdapter(getUsers(), this) //como la main activity implementa la interfaz podemos poner this

        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {
            setHasFixedSize(true) //indicamos que las vistas van a tener un tamanio definido asi que no debe preocuparse por esto = rendimiento
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User>{
        val users = mutableListOf<User>()

        val nicolas = User(1, "Nicolas", "Perez", "https://upload.wikimedia.org/wikipedia/commons/7/71/Argentina_team_in_St._Petersburg_%28cropped%29_Perez.jpg")
        val jorge = User(2,     "Jorge", "Meza", "https://wikimediafoundation.org/wp-content/uploads/2018/06/Vargas_Jorge.jpg")
        val maria = User(3, "Maria", "Gomez", "https://static1.elcorreo.com/www/multimedia/201807/04/media/cortadas/maria-gomez-kFUF-U60243842417dgC-624x385@RC.jpg")
        val victoria = User(4, "Victoria", "Garcia", "https://upload.wikimedia.org/wikipedia/commons/9/95/Ana_Victoria_Garc%C3%ADa_%28cropped%29.jpg")

        users.add(nicolas)
        users.add(jorge)
        users.add(maria)
        users.add(victoria)
        users.add(nicolas)
        users.add(jorge)
        users.add(maria)
        users.add(victoria)
        users.add(nicolas)
        users.add(jorge)
        users.add(maria)
        users.add(victoria)
        users.add(nicolas)
        users.add(jorge)
        users.add(maria)
        users.add(victoria)
        users.add(nicolas)
        users.add(jorge)
        users.add(maria)
        users.add(victoria)

        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }

}