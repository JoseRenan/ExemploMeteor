package br.edu.ifpb.androidddpclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;
import im.delight.android.ddp.MeteorSingleton;

public class MainActivity extends AppCompatActivity implements MeteorCallback, View.OnClickListener {

    private EditText edtPost;
    private Button btnPost;
    private ListView lstPosts;
    private ArrayAdapter<String> adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Twitter");
        setSupportActionBar(toolbar);

        list = new ArrayList<String>();

        btnPost = (Button) findViewById(R.id.btnPost);
        edtPost = (EditText) findViewById(R.id.edtPost);
        lstPosts = (ListView) findViewById(R.id.lstPosts);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        lstPosts.setAdapter(adapter);
        btnPost.setOnClickListener(this);

        MeteorSingleton.createInstance(this, "ws://192.168.2.108:3000/websocket");
        MeteorSingleton.getInstance().setCallback(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnect(boolean b) {
        Log.i("CONEXAO","Conectado");
    }

    @Override
    public void onDisconnect() {
        Log.i("CONEXAO","Desconectado U_U");
    }

    @Override
    public void onException(Exception e) {
        Log.e("ERRO", "Houve um erro", e);
    }

    @Override
    public void onDataAdded(String s, String s1, String s2) {
        if (s2 != null && !s2.isEmpty() && !s2.contains("{"+ s +":")) {

            Gson gson = new GsonBuilder().create();
            Post post = gson.fromJson(s2, Post.class);
            adapter.add(post.getText());

        } else {
            Log.w("AndroidDDPClient", "Valor nulo ou vazio");
        }

    }

    @Override
    public void onDataChanged(String s, String s1, String s2, String s3) {

    }

    @Override
    public void onDataRemoved(String s, String s1) {

    }

    @Override
    public void onClick(View v) {
        Post post = new Post();
        post.setText(edtPost.getText().toString());

        Map<String, Object> values = new HashMap<String, Object>();
        values.put("text", post.getText());

        MeteorSingleton.getInstance().insert("posts", values);

        edtPost.setText("");

    }
}
