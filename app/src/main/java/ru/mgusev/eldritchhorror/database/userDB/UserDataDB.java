package ru.mgusev.eldritchhorror.database.userDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mgusev.eldritchhorror.dao.GameDAO;
import ru.mgusev.eldritchhorror.dao.InvestigatorDAO;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;

@Database(entities = {Game.class, Investigator.class}, version = 1)
public abstract class UserDataDB extends RoomDatabase {
    public abstract GameDAO gameDAO();
    public abstract InvestigatorDAO investigatorDAO();
}