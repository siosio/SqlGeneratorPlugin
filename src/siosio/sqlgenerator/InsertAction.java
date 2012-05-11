package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;
import com.intellij.persistence.database.psi.DbElement;

public class InsertAction extends SqlGeneratorSupport {

    private static final SqlGenerator GENERATOR = new InsertAction.InsertGenerator();

    public InsertAction() {
        super("INSERT");
    }

    @Override
    SqlGenerator createSqlGenerator() {
        return GENERATOR;
    }

    @Override
    String getStatementType() {
        return "INSERT";
    }

    public static class InsertGenerator implements SqlGenerator {

        @Override
        public String generate(TableInfo tableInfo) {
            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO ").append(LF);
            sql.append("    ").append(tableInfo.getTableName()).append(LF);
            sql.append("    (").append(LF);
            StringBuilder values = new StringBuilder();
            List<? extends DbColumnElement> columns = tableInfo.getColumns();
            for (int i = 0, size = columns.size(); i < size; i++) {
                DbElement column = columns.get(i);
                if (i != 0) {
                    sql.append(",").append(LF);
                    values.append(",").append(LF);
                }
                values.append("    ?");
                sql.append("    ").append(column.getName());
            }
            sql.append(LF);
            sql.append("    )").append(LF);
            sql.append("VALUES").append(LF);
            sql.append("    (").append(LF);
            sql.append(values).append(LF);
            sql.append("    )").append(LF);
            return sql.toString();
        }
    }
}

