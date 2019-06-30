package ru.mgusev.eldritchhorror.database.staticDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mgusev.eldritchhorror.database.dao.AncientOneDAO;
import ru.mgusev.eldritchhorror.database.dao.CardDAO;
import ru.mgusev.eldritchhorror.database.dao.CardTypeDAO;
import ru.mgusev.eldritchhorror.database.dao.EndingDAO;
import ru.mgusev.eldritchhorror.database.dao.ExpansionDAO;
import ru.mgusev.eldritchhorror.database.dao.InvestigatorDAO;
import ru.mgusev.eldritchhorror.database.dao.PreludeDAO;
import ru.mgusev.eldritchhorror.database.dao.RumorDAO;
import ru.mgusev.eldritchhorror.database.dao.SpecializationDAO;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.model.CardType;
import ru.mgusev.eldritchhorror.model.CardsExpansions;
import ru.mgusev.eldritchhorror.model.Ending;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.model.Rumor;
import ru.mgusev.eldritchhorror.model.Specialization;

@Database(entities = {AncientOne.class, Expansion.class, Investigator.class, Prelude.class, Specialization.class, Rumor.class, Ending.class, CardType.class, Card.class, CardsExpansions.class}, version = 10)
public abstract class StaticDataDB extends RoomDatabase {
    public abstract AncientOneDAO ancientOneDAO();
    public abstract ExpansionDAO expansionDAO();
    public abstract PreludeDAO preludeDAO();
    public abstract InvestigatorDAO investigatorDAO();
    public abstract SpecializationDAO specializationDAO();
    public abstract RumorDAO rumorDAO();
    public abstract EndingDAO endingDAO();
    public abstract CardTypeDAO cardTypeDAO();
    public abstract CardDAO cardDAO();
}