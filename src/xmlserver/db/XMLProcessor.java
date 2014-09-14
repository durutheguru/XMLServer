/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver.db;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import javax.sql.rowset.JdbcRowSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Text;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.DOMException;

public class XMLProcessor {
   
    public static Document getResult(Document request) throws SQLException, DOMException, ParserConfigurationException {
        String db = extractDBFromRequest(request);
        String sql = extractSQLFromRequest(request);
        
        return getResult(db, sql);
    }
    
    public static boolean isSpecialRequest(Document request) {
        return request.getDocumentElement().getTagName().equals("_request");
    }
    
    public static String getSpecialRequestType(Document request){
        if (!isSpecialRequest(request))
            return "";
        
        return request.getDocumentElement().getAttribute("type");
    }
    
    public static Document getSpecialResponse(Document request) throws SQLException, DOMException, ParserConfigurationException{
        String db = extractDBFromRequest(request);
        String sql = extractSQLFromRequest(request);
        String type = getSpecialRequestType(request);
        
        if (type.equals("viewdb")){
            JdbcRowSet rSet = DBInterface.getResult(db, sql);
            return convertResultToXML(sql, rSet, true, type);
        }
        else
            return generateSuccessDocument(sql, 0);
    }
    
    public static Document getResult(String db, String sql) throws SQLException, DOMException, ParserConfigurationException {
        String _sql = sql.toLowerCase();
        
        if (DBInterface.isDML(_sql)){
            int affectedRows = DBInterface.getUpdate(db, sql);
            return generateSuccessDocument(sql, affectedRows);
        }
        else {
            JdbcRowSet rowSet = DBInterface.getResult(db, sql);
            return convertResultToXML(sql, rowSet);
        }
    }
    
    public static String extractSQLFromRequest(Document request) {
        Element root = request.getDocumentElement();
        Element sqlElement = (Element)root.getElementsByTagName("sql").item(0);
        Text sqlText = (Text)sqlElement.getChildNodes().item(0);
        
        return sqlText.getNodeValue();
    }
    
    public static String extractDBFromRequest(Document request) {
        Element root = request.getDocumentElement();
        String db = root.getAttribute("database");
        
        return db;
    }
    
    public static Document generateErrorDocument(String sql, Throwable t) throws DOMException, ParserConfigurationException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        
        Element root = document.createElement("result");
        root.setAttribute("sql", sql);
        
        Element error = document.createElement("error");
        error.appendChild(document.createTextNode(t.getMessage()));
        root.appendChild(error);
        
        document.appendChild(root);
        return document;
    }
    
    public static Document generateSuccessDocument(String sql, int affected) throws DOMException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        
        Element root = document.createElement("result");
        root.setAttribute("sql", sql);
        
        Element success = document.createElement("success");
        success.appendChild(document.createTextNode("Operation was carried out successfully. Affected rows: " + affected));
        root.appendChild(success);
        
        document.appendChild(root);
        return document;
    } 
    
    private static Document convertResultToXML(String sql, ResultSet result, boolean special, String type) throws SQLException, DOMException,
            ParserConfigurationException {
        ResultSetMetaData metaData = result.getMetaData();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        
        Element root = document.createElement((special ? "_" : "") + "result");
        root.setAttribute("sql", sql);
        if (special)
            root.setAttribute("type", type);
        
        Element columns = document.createElement("column-names");
        int colCount = metaData.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            Element col = document.createElement("column");
            col.appendChild(document.createTextNode(metaData.getColumnName(i)));
            columns.appendChild(col);
        }
        root.appendChild(columns);
        
        Element rSetData = document.createElement("resultset-data");
        while (result.next()){
            Element row = document.createElement("row");
            for (int i = 1; i <= colCount; i++){
                try{
                    Element data = document.createElement("data");
                    data.appendChild(document.createTextNode(result.getObject(i).toString()));
                    row.appendChild(data);
                }
                catch(Exception e){
                    continue;
                }
            }
            rSetData.appendChild(row);
        }
        root.appendChild(rSetData);
        
        document.appendChild(root);
        return document;
    }
    
    private static Document convertResultToXML(String sql, ResultSet result) throws SQLException, DOMException, ParserConfigurationException {
        return convertResultToXML(sql, result, false, null);
    }
    
}
