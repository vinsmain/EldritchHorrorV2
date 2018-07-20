package ru.mgusev.eldritchhorror.database.oldDB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;


public class InvestigatorOldDAO extends BaseDaoImpl {

    public InvestigatorOldDAO(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<InvestigatorOld> getInvestigatorsListByGameID(long gameID) throws SQLException {
        QueryBuilder<InvestigatorOld, Integer> qb = this.queryBuilder();
        qb.where().eq(InvestigatorOld.INVESTIGATOR_FIELD_GAME_ID, gameID);
        return qb.query();
    }
}
