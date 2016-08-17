package ir.elegam.doctor.Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Helper.Object_Extra;

public class database extends SQLiteOpenHelper {

	public final String path="data/data/ir.elegam.doctor/databases/";
	public final String DATABASE_NAME="dbdoctor";
	public SQLiteDatabase db;
	private String TAG = Variables.Tag;

	private SharedPreferences prefs;
	private int DATABASE_VERSION = 1;
	
	private final Context mycontext;

	private static final String TABLE_NEWS 		= "tblnews";
	private static final String TABLE_EXTRA 	= "tblextra";

	static final String Sid			= "Sid";        // 1
	static final String Faction		= "Faction";	// 2
	static final String Title 		= "Title";      // 3
	static final String Content 	= "Content";    // 4
	static final String ImageUrl 	= "ImageUrl";   // 5
	static final String Favorite 	= "Favorite";   // 6

	public database(Context context) {
		super(context, "dbdoctor", null, 1);
		mycontext=context;
		prefs = mycontext.getSharedPreferences("pf", 0);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {}

	@Override
	public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {}
	
	public void useable(){
		boolean checkdb=checkdb();
		if(checkdb){
			Log.i(TAG, "db exists");
			if(prefs.getInt("db", 1) < DATABASE_VERSION){
				Log.i(TAG, "new database");
				try{
					mycontext.deleteDatabase(DATABASE_NAME);
					copydatabase();
				}catch(IOException e){e.printStackTrace();}
			}
		}else{
			Log.i(TAG, "db notexists");
			this.getReadableDatabase();
			try{
			copydatabase();
			}catch(IOException e){e.printStackTrace();}
		}
	}
	
	public void open(){
		db = SQLiteDatabase.openDatabase(path+DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	public void close(){
		db.close();
	}
	
	public boolean checkdb(){
		SQLiteDatabase db = null;
		try{	
		db=SQLiteDatabase.openDatabase(path+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLException e) {e.printStackTrace();}
		return db != null ? true:false ;
	}
	
	public void copydatabase() throws IOException{
		OutputStream myOutput = new FileOutputStream(path+DATABASE_NAME);
		byte[] buffer = new byte[1024];
		int length;
		InputStream myInput = mycontext.getAssets().open(DATABASE_NAME);
		while ((length = myInput.read(buffer)) > 0) {
		myOutput.write(buffer, 0, length);
		}
		myInput.close();
		myOutput.flush();
		myOutput.close();

		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("db", DATABASE_VERSION);
		editor.commit();
		prefs = null;
	}

	public int CountAll(String Column,String Index){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+Column+" = '"+ Index +" ;", null);
		int s = cu.getCount();
		Log.i(TAG, "Count is: " + s);
		return s;
	}

	public String DisplayAll(int row,int field,String Column,String Index){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+Column+" = '"+ Index +" ;", null);
		cu.moveToPosition(row);
		String result = cu.getString(field);
		return result;
	}// end Display()

	public String DisplayExtra(String Column){
		Cursor cu = db.rawQuery("select * from "+ TABLE_EXTRA +" where Title = '"+ Column +"' ;", null);
		cu.moveToFirst();
		String result = cu.getString(2);
		return result;
	}// end Display()

	public String DisplayOne(int field,String MColumn,String MIndex,String Column,String Index){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+ MColumn +" = '"+ MIndex +"' and "+ Column +" = '"+ Index +"' ;", null);
		cu.moveToFirst();
		String result = cu.getString(field);
		return result;
	}// end Display()

	public void	update(String Column1, String newFav,String MColumn,String MIndex, String Column, String Index){
		db.execSQL("update "+ TABLE_NEWS +" set "+ Column1 +" = '" + newFav + "' where " + MColumn + " = '" + MIndex + "' and "+ Column +" = '"+ Index +"' ;");
		Log.i(TAG, "update");
	}

	public void Insert(MyObject ob){
		ContentValues cv = new ContentValues();
		cv.put(Sid, ob.getSid());
		cv.put(Faction,ob.getFaction());
		cv.put(Title,ob.getTitle());
		cv.put(Content,ob.getContent());
		cv.put(ImageUrl,ob.getImage_url());
		cv.put(Favorite,"0");
		db.insert(TABLE_NEWS, Sid, cv);
		Log.i(TAG, "insert");
	}

	public void InsertExtra(Object_Extra ob){
		ContentValues cv = new ContentValues();
		cv.put(Title, ob.getTitle());
		cv.put(Content,ob.getContent());
		db.insert(TABLE_EXTRA, Title, cv);
		Log.i(TAG, "insert");
	}

	public boolean CheckExistanceNews(String MColumn,String MIndex,String Column, String Index){
		Cursor cursor = db.rawQuery("select * from "+ TABLE_NEWS +" where "+ MColumn +" = '"+ MIndex +"'  and "+ Column +" = '"+ Index +"' ;", null);
		cursor.moveToFirst();
		int result = cursor.getCount();
		if(result == 0){
			Log.i(TAG,"false");
			return false;
		}
		else{
			Log.i(TAG,"true");
			return true;
		}
	}

	public boolean DeleteTabele1(String Column,String Index){
		Log.i(TAG,"delte");
		return db.delete(TABLE_NEWS, Column + "=" + Index, null) > 0;
	}

	@SuppressLint("DefaultLocale")
	public void DeleteTabele2(String Column, String Index) {
		db.execSQL(String.format(
				"DELETE FROM %s WHERE %s = %d",
				TABLE_NEWS,
				Column,Index
		));
	}


}// end class