package siosio.sqlgenerator;

public class SelectSqlGeneratorAction extends SqlGeneratorSupport {

    public SelectSqlGeneratorAction() {
        super("SELECT SQL");
    }

    @Override
    String getStatementType() {
        return "SELECT";
    }

    @Override
    String getSqlTemplate() {
        return  "SELECT" + Util.LF
                + "$COLUMN_LIST$" + Util.LF
                + "FROM" + Util.LF
                + "    $TABLE_NAME$" + Util.LF
                + "$WHERE_CLAUSE$" + Util.LF;
    }
}

