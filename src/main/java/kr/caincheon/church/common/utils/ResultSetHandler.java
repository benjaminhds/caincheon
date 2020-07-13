package kr.caincheon.church.common.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class ResultSetHandler
{

    public static List<Map<String, Object>> conversion(ResultSet _rs) throws SQLException
    {
    	return conversion(_rs, true);
    }
    
    public static List<Map<String, Object>> conversion(ResultSet _rs, boolean isColumnNameToUpperCase) throws SQLException {
        List<Map<String, Object>> rtRows = new ArrayList<Map<String, Object>>();
        if(_rs != null) {
            ResultSetMetaData meta = _rs.getMetaData();
            int cc = meta.getColumnCount();
            String keys[] = new String[cc];
            for(int i = 0; i < cc; i++)
                keys[i] = meta.getColumnName(i + 1);

            int toUpperCase = isColumnNameToUpperCase ? 1 : 0 ;
            LinkedHashMap<String, Object> row=null;
            //for(; _rs.next(); rtRows.add(row)) {
            while(_rs.next()) {
                row = new LinkedHashMap<String, Object>();
                for(int i = 0; i < cc; i++) {
                	switch(toUpperCase) {
                	case 0:
                		row.put(keys[i].toLowerCase(), _rs.getObject(keys[i]));
                		break;
                	case 1:
                		row.put(keys[i].toUpperCase(), _rs.getObject(keys[i]));
                		break;
                	}
                }
                rtRows.add(row);
            }
        }
        return rtRows;
    }

    public static LinkedHashMap<String, Object> conversionFirstRow(ResultSet _rs) throws SQLException {
    	return conversionFirstRow(_rs, true);
    }
    public static LinkedHashMap<String, Object> conversionFirstRow(ResultSet _rs, boolean isColumnNameToUpperCase) throws SQLException {
    	LinkedHashMap<String, Object> rtRow = new LinkedHashMap<String, Object>();
        if(_rs != null) {
            ResultSetMetaData meta = _rs.getMetaData();
            int cc = meta.getColumnCount();
            String columnn[] = new String[cc];
            for(int i = 0; i < cc; i++)
                columnn[i] = meta.getColumnName(i + 1);
            
            int toUpperCase = isColumnNameToUpperCase ? 1 : 0 ;
            if(_rs.next()) {
            	for(int i = 0; i < cc; i++) {
            		Object od = _rs.getObject(columnn[i]);
                	switch(toUpperCase) {
                	case 0:
                		rtRow.put(columnn[i].toLowerCase(), od==null ? "" : od );
                		break;
                	case 1:
                		rtRow.put(columnn[i].toUpperCase(), od==null ? "" : od );
                		break;
                	}
                }
            }
        }
        return rtRow;
    }

    public static LinkedHashMap<String, Object> conversionToMap(ResultSet _rs) throws SQLException {
        LinkedHashMap<String, Object> rtRows = new LinkedHashMap<String, Object>();
        if(_rs != null)
            for(; _rs.next(); rtRows.put(_rs.getObject(1).toString(), _rs.getObject(2)));
        return rtRows;
    }
}
