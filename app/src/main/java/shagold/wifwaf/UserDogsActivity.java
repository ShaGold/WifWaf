package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;
import java.util.List;
import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;
import shagold.wifwaf.list.DogAdapter;

public class UserDogsActivity extends AppCompatActivity {

    private Socket mSocket;
    private int midUser;
    private Button addDog;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dogs);
        initListView();

        mSocket = SocketManager.getMySocket();
        midUser = SocketManager.getIdUser();
        mSocket.on("RGetAllMyDogs", onRGetAllMyDogs);
        mSocket.emit("getAllMyDogs", midUser);

        List<Dog> dogs = generateDogs();
        DogAdapter adapter = new DogAdapter(this, dogs);
        mListView.setAdapter(adapter);

        final Intent activityAddDog = new Intent(getApplicationContext(), AddDogActivity.class);

        addDog = (Button) findViewById(R.id.addDogButton);
        addDog.setBackgroundColor(WifWafColor.BROWN_DARK);
        addDog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(activityAddDog);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.defaultMenu(this, item) || super.onOptionsItemSelected(item);
    }

    private List<Dog> generateDogs() {
        List<Dog> dogs = new ArrayList<Dog>();

        dogs.add(new Dog(1, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(2, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(3, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(4, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(5, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(6, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(7, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(8, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));

        return dogs;
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.dogListView);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Dog dog = (Dog) mListView.getItemAtPosition(position);
                //Toast.makeText(getBaseContext(), dog.getName(), Toast.LENGTH_SHORT).show();
                Intent actDogProfile = new Intent(getApplicationContext(), DogProfileActivity.class);
                startActivity(actDogProfile);
            }
        });
    }

    private Emitter.Listener onRGetAllMyDogs = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            UserDogsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    System.out.println(args[0]);

                    //Log.d("LOG", args[0].toString());

                }

            });
        }

    };
}
