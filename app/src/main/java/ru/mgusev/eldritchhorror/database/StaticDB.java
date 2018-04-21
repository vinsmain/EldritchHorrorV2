package ru.mgusev.eldritchhorror.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mgusev.eldritchhorror.dao.AncientDAO;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;

@Database(entities = {AncientOne.class, Expansion.class, Investigator.class, Prelude.class}, version = 6)
public abstract class StaticDB extends RoomDatabase {
    public abstract AncientDAO ancientDAO();
}
