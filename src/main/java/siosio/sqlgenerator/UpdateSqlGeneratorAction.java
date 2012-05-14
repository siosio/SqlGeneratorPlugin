package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;

public class UpdateSqlGeneratorAction extends SqlGeneratorSupport {

    private static final SqlGenerator GENERATOR = new UpdateGenerator();

    public UpdateSqlGeneratorAction() {
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

    private static final String SQL_TEMPLATE =
            "UPDATE\n" + "    $TABLE_NAME$\n" + "SET\n" + "$SET_LIST$\n" + "$WHERE_LIST$\n";

    private static class UpdateGenerator implements SqlGenerator {

        @Override
        public String generate(TableInfo tableInfo) {

            List<? extends DbColumnElement> columns = tableInfo.getNonPrimaryColumns();
            StringBuilder setList = new StringBuilder();
            for (int i = 0, size = columns.size(); i < size; i++) {
                DbColumnElement column = columns.get(i);
                if (i != 0) {
                    setList.append(",").append(LF);
                }
                setList.append("    ").append(column.getName()).append(" = ?");
            }

            String sql = SQL_TEMPLATE.replace("$TABLE_NAME$", tableInfo.getTableName()).replace("$SET_LIST$", setList);

            List<DbColumnElement> primaryKeys = tableInfo.getPrimaryKeys();
            if (primaryKeys.isEmpty()) {
                return sql;
            }

            StringBuilder whereList = new StringBuilder();
            whereList.append("WHERE").append(LF);
            for (int i = 0, size = primaryKeys.size(); i < size; i++) {
                DbColumnElement key = primaryKeys.get(i);
                whereList.append("    ");
                if (i != 0) {
                    whereList.append("AND ");
                }
                whereList.append(key.getName()).append(" = ?").append(LF);
            }
            return sql.replace("$WHERE_LIST$", whereList);
        }
    }
}
