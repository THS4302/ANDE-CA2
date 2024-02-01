package com.example.tripsavvy_studio_2b03_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;

public class CardDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "cardManager";
    private static final String TABLE_CARDS = "cards";
    private static final String KEY_ID = "card_id";
    private static final String KEY_CARD_NUMBER = "cardNumber";
    private static final String KEY_EXPIRATION_DATE = "expirationDate";
    private static final String KEY_CVV = "cvv";

    public CardDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARDS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CARD_NUMBER + " TEXT,"
                + KEY_EXPIRATION_DATE + " TEXT,"
                + KEY_CVV + " TEXT" + ")";
        db.execSQL(CREATE_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

    public void addCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CARD_NUMBER, card.getCardNumber());
        values.put(KEY_EXPIRATION_DATE, card.getExpirationDate());
        values.put(KEY_CVV, card.getCvv());

        db.insert(TABLE_CARDS, null, values);
        db.close();
    }

    public List<Card> getAllCards() {
        List<Card> cardList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CARDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setCardId(Integer.parseInt(cursor.getString(0)));
                card.setCardNumber(cursor.getString(1));
                card.setExpirationDate(cursor.getString(2));
                card.setCvv(cursor.getString(3));

                cardList.add(card);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cardList;
    }

    public Card getCard(int cardId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARDS, new String[]{KEY_ID, KEY_CARD_NUMBER, KEY_EXPIRATION_DATE, KEY_CVV},
                KEY_ID + "=?", new String[]{String.valueOf(cardId)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Card card = new Card();
        card.setCardId(Integer.parseInt(cursor.getString(0)));
        card.setCardNumber(cursor.getString(1));
        card.setExpirationDate(cursor.getString(2));
        card.setCvv(cursor.getString(3));

        return card;
    }

    public Card getCardByDetails(String cardNumber, String expirationDate, String cvv) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARDS, new String[]{KEY_ID, KEY_CARD_NUMBER, KEY_EXPIRATION_DATE, KEY_CVV},
                KEY_CARD_NUMBER + "=? AND " + KEY_EXPIRATION_DATE + "=? AND " + KEY_CVV + "=?",
                new String[]{cardNumber, expirationDate, cvv}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Card card = new Card();
            card.setCardId(Integer.parseInt(cursor.getString(0)));
            card.setCardNumber(cursor.getString(1));
            card.setExpirationDate(cursor.getString(2));
            card.setCvv(cursor.getString(3));

            cursor.close();
            return card;
        } else {
            return null;
        }

    }

    public void deleteCard(int cardId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARDS, KEY_ID + "=?", new String[]{String.valueOf(cardId)});
        db.close();
    }


}
