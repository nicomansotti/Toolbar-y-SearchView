package com.androiddesdecero.toolbar;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
    Toolbar "Barra de Herramientas" -> Antes conocida como Action Bar "Barra de Acción"
    La Toolbar es más flexible que la ActionBar.
    En la Toolbar se puede modifcar el color, tamaño y posición de una manera muy sencilla.
    Se pueden agregar logos, etiquetas, iconos de navegación y otras vistas y el titulo de la activity.
    Támpien se puede utilizar como navegación de la aplicación.
    La podemos diseñar en nuestro archivo layout
    Por ello se recomienda el uso del Toolbar vs ActionBar
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CursoAdapter cursoAdapter;
    private List<Curso> cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolBar();
        hideToolBar();
        showToolBar();
        data();
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lim);
        cursoAdapter = new CursoAdapter(cursos);
        recyclerView.setAdapter(cursoAdapter);
    }

    private void data(){
        cursos = new ArrayList<>();
        cursos.add(new Curso("Alberto", "Curso Básico Room"));
        cursos.add(new Curso("Marta", "Curso Básico iOS"));
        cursos.add(new Curso("Maria", "Curso Básico Programacion"));
        cursos.add(new Curso("Alberto", "Curso Básico Retrofit"));
        cursos.add(new Curso("Pablo", "Curso Básico Kotlin"));

        cursos.add(new Curso("Alberto", "Curso Intermedio Room"));
        cursos.add(new Curso("Marta", "Curso Intermedio iOS"));
        cursos.add(new Curso("Maria", "Curso Intermedio Programacion"));
        cursos.add(new Curso("Alberto", "Curso Intermedio Retrofit"));
        cursos.add(new Curso("Pablo", "Curso Intermedio Kotlin"));

        cursos.add(new Curso("Alberto", "Curso Avanzado Room"));
        cursos.add(new Curso("Marta", "Curso Avanzado iOS"));
        cursos.add(new Curso("Maria", "Curso Avanzado Programacion"));
        cursos.add(new Curso("Alberto", "Curso Avanzado Retrofit"));
        cursos.add(new Curso("Pablo", "Curso Avanzado Kotlin"));
    }

    private void setUpToolBar(){
        toolbar = findViewById(R.id.toolbar);
        //Ponemos nuestra toolbar como ActionBar
        setSupportActionBar(toolbar);

        showHomeUpIcon();
        setUpHomeUpIconAndColor(R.drawable.ic_camera, R.color.colorWhiteApp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "homeButton - setNavigationOnClickListener");
            }
        });

        customTitleToolBar();
    }

    //Nos crea el menú de opciones en la Action Bar y aparecen los tres puntos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menuIconColor(menu, R.color.colorWhiteApp);
        MenuItem searchItem = menu.findItem(R.id.new_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textoFiltrado) {
                cursoAdapter.getFilter().filter(textoFiltrado);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //Los Iconos les podemos poner el color a través de este metodo
    public void menuIconColor(Menu menu, int color){
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    //Para saber que item ha sido seleccionado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Log.d("TAG", "homeButton - onOptionsItemSelected");
                break;
            case R.id.new_search:
                Log.d("TAG1", "New Email");
                break;
            case R.id.send_email:
                Log.d("TAG1", "Send Email");
                break;
            case R.id.update_settings:
                Log.d("TAG1", "Update Settings");
                break;
            case R.id.send_email_secure:
                Log.d("TAG1", "Send Email Secure");
                break;
            case R.id.send_email_unsecure:
                Log.d("TAG1", "Send Email UnSecure");
                break;
            default:
                //Error Desconocido
        }
        return super.onOptionsItemSelected(item);
    }

    //Se ejecuta cuando se pulsa el boton Navigate Up
    @Override
    public boolean onSupportNavigateUp() {
        Log.d("TAG", "homeButton - onSupportNavigateUp");
        return true;
    }

    private void hideToolBar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void showToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    /*
    setDisplayHomeAsUpEnabled(true) -> Este método nos muestra el icono y además que se pueda presionar.
    Pero para implementar funcionalidad hay 3 opcione
    1.- Añadir en el manifest android: parentActivityName
    2.- Implementar setNavigationOnclikcListener
    3.- @Override public boolean onSupportNavigateUp() {}
     */
    public void showHomeUpIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void hideHomeUpIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    //Para poner el color y el Icono de Home
    private void setUpHomeUpIconAndColor(int drawable, int color){
        if (getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(drawable);
            upArrow.setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    private void customTitleToolBar(){
        if (getSupportActionBar() != null) {
            //Ocultamos el titulo por defecto de la Toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView textView = toolbar.findViewById(R.id.toolbar_title);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "Title Toolbar");
                }
            });
        }
    }
}