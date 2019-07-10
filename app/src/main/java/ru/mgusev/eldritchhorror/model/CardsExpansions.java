package ru.mgusev.eldritchhorror.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "cards_expansions", primaryKeys = {"card_id","expansion_id"})
public class CardsExpansions {

    @ColumnInfo(name = "card_id")
    private int cardID;

    @ColumnInfo(name = "expansion_id")
    private int expansionID;

    public CardsExpansions() {
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getExpansionID() {
        return expansionID;
    }

    public void setExpansionID(int expansionID) {
        this.expansionID = expansionID;
    }
}
