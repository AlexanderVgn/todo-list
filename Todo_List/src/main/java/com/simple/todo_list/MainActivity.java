package com.simple.todo_list;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    static final private int ADD_NEW_TODO = Menu.FIRST;

    static final private int REMOVE_TODO = Menu.FIRST + 1;

    private boolean addingNew = false;

    private ArrayList<String> todoItems;

    private ListView myListView;

    private EditText myEditText;

    private ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItems = new ArrayList<String>();
        myListView = (ListView) findViewById(R.id.myListView);
        myEditText = (EditText) findViewById(R.id.myEditText);

        int resID = R.layout.todolist_item;
        aa = new ArrayAdapter<String>(this, resID, todoItems);
        myListView.setAdapter(aa);

        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                     if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        todoItems.add(0, myEditText.getText().toString());
                        myEditText.setText("");
                        aa.notifyDataSetChanged();
                        cancelAdd();
                        return true;
                    }
                return false;
            }
        });

        registerForContextMenu(myListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem itemAdd = menu.add(0, ADD_NEW_TODO, Menu.NONE,
                R.string.add_new);
        MenuItem itemRem = menu.add(0, REMOVE_TODO, Menu.NONE,
                R.string.remove);

        itemAdd.setIcon(R.drawable.add_new_item);
        itemRem.setIcon(R.drawable.remove_item);

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Selected To Do Item");
        menu.add(0,REMOVE_TODO, Menu.NONE, R.string.remove);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        int idx = myListView.getSelectedItemPosition();

        String removeTitle = getString(addingNew ? R.string.cancel : R.string.remove );

        MenuItem removeItem = menu.findItem(REMOVE_TODO);
        removeItem.setTitle(removeTitle);
        removeItem.setVisible(addingNew || idx > -1 );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        int index = myListView.getSelectedItemPosition();

        switch (item.getItemId()){
            case (REMOVE_TODO): {
                if (addingNew) {
                    cancelAdd();
                }
                else {
                    removeItem(index);
                }
                return true;
            }
            case (ADD_NEW_TODO): {
                addNewItem();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        switch (item.getItemId()){
            case (REMOVE_TODO): {
                AdapterView.AdapterContextMenuInfo menuInfo;
                menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int index = menuInfo.position;

                removeItem(index);
                return true;
            }
        }

        return false;
    }

    private void cancelAdd() {
        addingNew = false;
        myEditText.setVisibility(View.GONE);
    }

    private void addNewItem() {
        addingNew = true;
        myEditText.setVisibility(View.VISIBLE);
        myEditText.requestFocus();
    }

    private void removeItem(int _index) {
        todoItems.remove(_index);
        aa.notifyDataSetChanged();
    }
}
