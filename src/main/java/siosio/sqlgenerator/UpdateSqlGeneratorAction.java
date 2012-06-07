package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;
import org.jetbrains.annotations.Nullable;

import static siosio.sqlgenerator.Util.LF;
import static siosio.sqlgenerator.Util.convertCamelCase;
import static siosio.sqlgenerator.Util.makeNamedWhereClause;

public class UpdateSqlGeneratorAction extends SqlGeneratorSupport {

    public UpdateSqlGeneratorAction() {
        super("UPDATE SQL");
    }

    public UpdateSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "UPDATE";
    }

    @Override
    String getSqlTemplate() {
        return "UPDATE" + LF
                + "    $TABLE_NAME$"+ LF
                + "SET" + LF
                + "$SET_CLAUSE$" + LF
                + "$WHERE_CLAUSE$" + LF;
    }

    public static class NamedParameterSqlGeneratorAction extends UpdateSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("UPDATE SQL(named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                protected String getSetClause() {
                    List<? extends DbColumnElement> columns = tableInfo.getNonPrimaryColumns();

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < columns.size(); i++) {
                        DbColumnElement element = columns.get(i);
                        if (i != 0) {
                            sb.append(',').append(LF);
                        }
                        String columnName = element.getName();
                        sb.append("    ").append(columnName).append(" = :").append(convertCamelCase(columnName));
                    }
                    return sb.toString();
                }

                @Override
                protected String getWhereClause() {
                    return makeNamedWhereClause(tableInfo.getPrimaryKeys());
                }
            };
        }
    }
}

