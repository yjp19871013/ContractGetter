package com.example.yjp.contractgetter

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private val LOADER_ID = 0
        private val PHONE_PROJECTION = arrayOf(Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER)
    }

    private var mAdapter:SimpleCursorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                null,
                arrayOf(Phone.DISPLAY_NAME, Phone.NUMBER),
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0)
        listView.adapter = mAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener {
            _, _, position, _ ->
            val cursor = listView.getItemAtPosition(position) as Cursor
            val displayNameIndex = cursor.getColumnIndex(Phone.DISPLAY_NAME)
            Toast.makeText(this, cursor.getString(displayNameIndex), Toast.LENGTH_SHORT).show()
        }

        loaderManager.initLoader(LOADER_ID, null, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        loaderManager.destroyLoader(LOADER_ID)
    }

    override fun onCreateLoader(id: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(this,
                Phone.CONTENT_URI,
                PHONE_PROJECTION,
                null,
                null,
                Phone.DISPLAY_NAME)
    }

    override fun onLoaderReset(cursor: Loader<Cursor>?) {
        mAdapter?.swapCursor(null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        mAdapter?.swapCursor(cursor)
    }
}
