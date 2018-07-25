package ru.mgusev.eldritchhorror.database.oldDB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class GameOldDAO extends BaseDaoImpl {
    public GameOldDAO(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<GameOld> getGamesSortDateUp() throws SQLException{
        QueryBuilder<GameOld, Integer> qb = this.queryBuilder();
        qb.orderBy(GameOld.GAME_FIELD_DATE, true);
        qb.orderBy(GameOld.GAME_FIELD_ID, true);
        return qb.query();
    }

    public void writeGameToDB(GameOld gameOld) throws SQLException {
        this.createOrUpdate(gameOld);
    }
}