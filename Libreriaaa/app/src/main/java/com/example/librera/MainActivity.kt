package com.example.librera

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librera.models.libro
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout


    //lateinit var listlibro:MutableList<libro>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        //se inicializa el mutablelist
        /*listlibro= mutableListOf()
        //se agrega los elementos
        var libro=libro("El jardín de las mariposas","Dot Hutchison", "Thriller")
        listlibro.add(libro)
        listlibro.add(libro("Julián","Aprendiz"))
        listlibro.add(libro("Valentina","Aprendiz"))
        listlibro.add(libro("Iván","Aprendiz"))
        listlibro.add(libro("Mariana","Aprendiz"))
        listlibro.add(libro("Patricia","Aprendiz"))

        /*Se crea y se asocia una variable con el objeto de la vista*/
        var recycler=findViewById<RecyclerView>(R.id.RVLibros)
        recycler.layoutManager= LinearLayoutManager(applicationContext)
        //se crea el adaptador
        var adapterLibros= adapterLibros.adapterLibros(listlibro, applicationContext)
        // se asocia el adaptador con el objeto
        recycler.adapter=adapterLibros*/

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_book -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, guardarLibroFragment()).commit()
            R.id.nav_logout -> Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}