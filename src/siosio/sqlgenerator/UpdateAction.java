package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;

public class UpdateAction extends SqlGeneratorSupport {

    private static final SqlGenerator GENERATOR = new UpdateGenerator();

    public UpdateAction() {
        super("UPDATE");
    }

    @Override
    String getStatementType() {
        return "UPDATE";
    }

    @Override
    SqlGenerator createSqlGenerator() {
        return GENERATOR;
    }

    private static class UpdateGenerator implements SqlGenerator {

        @Override
        public String generate(TableInfo tableInfo) {
            StringBuilder sql = new StringBuilder();

            List<? extends DbColumnElement> columns = tableInfo.getNonPrimaryColumns();
            List<DbColumnElement> primaryKeys = tableInfo.getPrimaryKeys();

            sql.append("UPDATE").append(LF);
            sql.append("    ").append(tableInfo.getTableName()).append(LF);
            sql.append("SET").append(LF);

            for (int i = 0, size = columns.size(); i < size; i++) {
                DbColumnElement column = columns.get(i);
                if (i != 0) {
                    sql.append(",").append(LF);
                }
                sql.append("    ").append(column.getName()).append(" = ?");
            }
            sql.append(LF);
            if (primaryKeys.isEmpty()) {
                return sql.toString();
            }

            sql.append("WHERE").append(LF);
            for (int i = 0, size = primaryKeys.size(); i < size; i++) {
                DbColumnElement key = primaryKeys.get(i);
                sql.append("    ");
                if (i != 0) {
                    sql.append("AND ");
                }
                sql.append(key.getName()).append(" = ?").append(LF);
            }
            return sql.toString();
        }
    }
}
