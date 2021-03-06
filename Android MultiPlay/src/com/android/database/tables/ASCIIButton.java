package com.android.database.tables;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.provider.BaseColumns;
import android.util.Log;

import com.android.database.DBHelper;

/** Class that is representing table with the same name in application's database.
 * 
 * This is a helper class that generates queries relevant to the table 
 * that this class represents. The example below shows how to deal with
 * any {@link Map} type arguments such as "newValues" in {@link DBHelper#sql_insert_row(Class, Map, boolean)}:
 * 
 * {@code
 * Map<String,String> newValues = new HashMap<String,String>();
 * 	newValues.put(ASCIIButton.DBSchema.COLUMN_1, "[Crtl]+[A]");
 * 	newValues.put(ASCIIButton.DBSchema.COLUMN_2, "[Crtl]+[B]");
 * ASCIIButton.SQL_UPDATE_ROW(1, newValues);
 * }
 *
 * 
 * where the keys are the names of the columns in the corresponding table,
 *  which this class represents.
 *
 * @author tomasz
 */
public class ASCIIButton implements DBHelper.TableIF {

	/** Inner class for {@link ASCIIButton} that implements {@link BaseColumns}.
	 * 
	 * Additionally, it stores the names of the columns, along with all the other values
	 * that are important to the table, as static final {@link String}s to help you build queries.
	 * All column names are listed like follows:
	 * 
	 * <ul>
	 * 	<li> COLUMN_1 </li>
	 * 	<li> COLUMN_2 </li>
	 * 	<li> ... </li>
	 * 	<li> KEY_UNIQUE_1 </li>
	 * 	<li> KEY_UNIQUE_2 </li>
	 * 	<li> ... </li>
	 * </ul>
	 * 
	 * and so another table attributes that might be used. Be aware that although values 
	 * from KEY_UNIQUE_2 to KEY_UNIQUE_n are optional, values KEY_UNIQUE_1 and TABLE_NAME 
	 * are required to provide correct functionality and they should not be changed.
	 *
	 */
	public static abstract class DBSchema implements BaseColumns {
		public static final String TABLE_NAME = "ASCIIButton";
        public static final String COLUMN_1 = "ShortTap_Key_Combination";
        public static final String COLUMN_2 = "LongTap_Key_Combination";
        public static final String KEY_UNIQUE_1 = "ASCIIButtonID";
	}
	
	/** Implementation of {@link DBHelper.TableIF#getTableName()} method.
	 * 
	 * As mentioned here ({@link DBSchema}), {@link DBSchema#TABLE_NAME} value is necessary 
	 * for full functionality and they should not be changed.
	 */
	@Override
	public String getTableName() {
		return DBSchema.TABLE_NAME;
	}

	/** Implementation of {@link DBHelper.TableIF#getKeyUnique1()} method.
	 * 
	 * As mentioned here ({@link DBSchema}), {@link DBSchema#KEY_UNIQUE_1} value is necessary 
	 * for full functionality and they should not be changed.
	 */
	@Override
	public String getKeyUnique1() {
		return DBSchema.KEY_UNIQUE_1;
	}

	/** Generates String that contains query to database for creating ASCIIButton table.
	 * 
	 */
	public static final String SQL_CREATE_TABLE =
	    "CREATE TABLE IF NOT EXISTS " + DBSchema.TABLE_NAME + " ("
	    		+ DBSchema._ID + " " + DBHelper.TYPE_PK_INT + ", "
	    		+ DBSchema.COLUMN_1 + " " + DBHelper.TYPE_TEXT + ", "
	    		+ DBSchema.COLUMN_2 + " " + DBHelper.TYPE_TEXT + ", "
	    		+ DBSchema.KEY_UNIQUE_1 + " " + DBHelper.TYPE_SK_INT
	    	+ " )";

	/** Generates String that contains query to database for deleting ASCIIButton table.
	 * 
	 */
	public static final String SQL_DROP_TABLE =
	    "DROP TABLE IF EXISTS " + DBSchema.TABLE_NAME;
	
	
	
	/** Generates String that contains query to database for updating rows in ASCIIButton table.
	 * 
	 * @param id {@link DBSchema#KEY_UNIQUE_1} value that uniquely represents one row in ASCIIButton table.
	 * @param newValues Set of pairs in form: ({@literal <}COLUMN_NAME{@literal >},{@literal <}NEW_VALUE{@literal >}) (<a href="#example">Usage example</a>).
	 * @return String that holds a new query for database that updates single row in ASCIIButton table.
	 */
	public static String SQL_UPDATE_ROW (int id, HashMap<String, String> newValues) {
		Iterator<Entry<String, String>> entry_IT = newValues.entrySet().iterator();
		Entry<String, String> curEntry = null;
		StringBuilder query = new StringBuilder("UPDATE " + DBSchema.TABLE_NAME + " SET ");
		while ( entry_IT.hasNext() ) {
			curEntry = entry_IT.next();
			query.append(curEntry.getKey() + " = " + curEntry.getValue());
			if ( entry_IT.hasNext() ) {
				query.append(", ");
			}
		}
		query.append(" WHERE " + DBSchema.KEY_UNIQUE_1 + " = " + id);
		Log.i("DB",query.toString());
		return query.toString();
	}

	
	/** String that contains query to database for inserting rows in ASCIIButton table.
	 * 
	 * @param id {@link DBSchema#KEY_UNIQUE_1} value that will uniquely represents new row in ASCIIButton table.
	 * @param newValues Set of pairs in form: ({@literal <}COLUMN_NAME{@literal >},{@literal <}NEW_VALUE{@literal >}) (<a href="#example">Usage example</a>).
	 * @return String that holds a new query for database that inserts single row in ASCIIButton table.
	 */
	public static String SQL_INSERT_ROW (int id, HashMap<String, String> newValues) {
		Iterator<String> keys_IT = newValues.keySet().iterator();
		Iterator<String> values_IT = newValues.values().iterator();
		StringBuilder query = new StringBuilder("INSERT INTO " + DBSchema.TABLE_NAME + " ( ");
		while ( keys_IT.hasNext() ) {
			query.append(keys_IT.next());
			if ( keys_IT.hasNext() ) {
				query.append(", ");
			}
		}
		query.append(" ) VALUES ( ");
		while ( values_IT.hasNext() ) {
			query.append(values_IT.next());
			if ( values_IT.hasNext() ) {
				query.append(", ");
			}
		}
		query.append(" ) ");
		Log.i("DB",query.toString());
		return query.toString();
	}
	
	public static String SQL_DELETE_ROW (int id) {
		StringBuilder query = new StringBuilder("DELETE FROM " + DBSchema.TABLE_NAME 
				+ " WHERE " + DBSchema.KEY_UNIQUE_1 + " = " + id);
		Log.i("DB",query.toString());
		return query.toString();	
	}
}