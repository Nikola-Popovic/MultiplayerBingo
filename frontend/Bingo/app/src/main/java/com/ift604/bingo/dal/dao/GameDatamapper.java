package com.ift604.bingo.dal.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.ift604.bingo.model.Ball;
import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.HashMap;

public class GameDatamapper {
    public static Card buildCard(String jsonCard) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Card> deserializer = new JsonDeserializer<Card>() {
            @Override
            public Card deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                int id = jsonObject.get("id").getAsInt();
                int lobbyId = jsonObject.get("lobbyId").getAsInt();
                HashMap<Coordinate, Box> bingoNumbers = new HashMap();
                JsonArray boxes = jsonObject.get("cases").getAsJsonArray();
                mapColumns(bingoNumbers, boxes);
                return new Card(id, lobbyId, bingoNumbers,  boxes.size(), boxes.get(0).getAsJsonArray().size());
            }

            private void mapColumns(HashMap<Coordinate, Box> bingoNumbers, JsonArray boxes) {
                for (int i = 0; i < boxes.size(); i++) {
                    JsonArray column = boxes.get(i).getAsJsonArray();
                    mapRows(bingoNumbers, boxes, i, column);
                }
            }

            private void mapRows(HashMap<Coordinate, Box> bingoNumbers, JsonArray boxes, int i, JsonArray column) {
                for (int j = 0; j < column.size(); j++) {
                    Coordinate coordinate = new Coordinate(i,j);
                    String boxNumber = buildFreeMiddleBox(boxes, i, j);
                    Box box = new Box(boxNumber, false);
                    bingoNumbers.put(coordinate, box);
                }
            }

            private String buildFreeMiddleBox(JsonArray boxes, int i, int j) {
                String boxNumber;
                JsonElement row = boxes.get(i).getAsJsonArray().get(j);
                if(row.getAsInt() == 0) {
                        boxNumber = "Free";
                }
                else {
                    boxNumber = row.getAsNumber().toString();
                }
                return boxNumber;
            }

        };
        gsonBuilder.registerTypeAdapter(Card .class,deserializer);
            Gson customGson = gsonBuilder.create();
            return customGson.fromJson(jsonCard, Card.class);
        }

    public static Ball buildBoule(String bouleJsonInfo) {
        JsonObject jsonObject = JsonParser.parseString(bouleJsonInfo).getAsJsonObject();
        Ball boule = new Ball(jsonObject.get("nextBoule").getAsString(), jsonObject.get("lobbyId").getAsInt());
        return boule;
    }

    public Boolean mapWinGameResult(String isValidJson) throws JSONException {
        JsonObject jsonObject = JsonParser.parseString(isValidJson).getAsJsonObject();
        return jsonObject.get("valide").getAsBoolean();
    }
}
