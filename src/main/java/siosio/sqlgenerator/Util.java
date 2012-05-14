package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;

public final class Util {

    /** 改行 */
    protected static final String LF = System.getProperty("line.separator");

    private Util() {
    }

    /**
     * WHERE句を構築する。
     *
     * @param columns WHERE句を構築するカラムのリスト
     * @return 構築したWHERE句
     */
    public static String makeWhereClause(List<DbColumnElement> columns) {
        if (columns.isEmpty()) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE").append(Util.LF);
        for (int i = 0, size = columns.size(); i < size; i++) {
            DbColumnElement column = columns.get(i);
            whereClause.append("    ");
            if (i != 0) {
                whereClause.append("AND ");
            }
            whereClause.append(column.getName()).append(" = ?").append(LF);
        }
        return whereClause.toString();
    }
}

