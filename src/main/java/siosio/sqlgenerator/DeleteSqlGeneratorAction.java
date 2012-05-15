package siosio.sqlgenerator;

public class DeleteSqlGeneratorAction extends SqlGeneratorSupport {

    public DeleteSqlGeneratorAction() {
        super("DELETE SQL");
    }

    @Override
    String getStatementType() {
        return "DELETE";
    }

    @Override
    String getSqlTemplate() {
        return "DELETE FROM" + Util.LF
                + "    $TABLE_NAME$" + Util.LF
                + "$WHERE_CLAUSE$" + Util.LF;
    }
}


