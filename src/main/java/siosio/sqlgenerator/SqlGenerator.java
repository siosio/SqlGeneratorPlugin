package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;
import com.intellij.persistence.database.psi.DbElement;

public class SqlGenerator {

    private TableInfo tableInfo;

    protected SqlGenerator(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    protected String getColumnList() {
        List<? extends DbColumnElement> columns = tableInfo.getColumns();
        StringBuilder columnList = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            DbElement column = columns.get(i);
            if (i != 0) {
                columnList.append(",").append(Util.LF);
            }
            columnList.append("    ").append(column.getName());
        }
        return columnList.toString();
    }

    protected String getWhereClause() {
        return Util.makeWhereClause(tableInfo.getPrimaryKeys());
    }

    protected String getInsertValues() {
        List<? extends DbColumnElement> columns = tableInfo.getColumns();
        StringBuilder columnList = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            if (i != 0) {
                columnList.append(",").append(Util.LF);
            }
            columnList.append("    ").append("?");
        }
        return columnList.toString();
    }

    protected String getSetClause() {
        List<? extends DbColumnElement> columns = tableInfo.getNonPrimaryColumns();
        StringBuilder setClause = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            DbColumnElement columnElement = columns.get(i);
            if (i != 0) {
                setClause.append(",").append(Util.LF);
            }
            setClause.append("    ").append(columnElement.getName()).append(" = ?");
        }
        return setClause.toString();
    }
}

