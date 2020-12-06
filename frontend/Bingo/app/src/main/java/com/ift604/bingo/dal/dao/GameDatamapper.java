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
import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;

import org.json.JSONException;
import org.json.JSONObject;

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
                for (int i = 0; i < boxes.size(); i++) {
                    //todo mettre le get.getasarray dans un obj
                    for (int j = 0; j < boxes.get(i).getAsJsonArray().size(); j++) {
                        Coordinate coordinate = new Coordinate(i,j);
                        String boxNumber;
                        if(boxes.get(i).getAsJsonArray().get(j).getAsInt() == 0) {
                                boxNumber = "Free";
                        }
                        else {
                            boxNumber = boxes.get(i).getAsJsonArray().get(j).getAsNumber().toString();
                        }
                        Box box = new Box(boxNumber, false);
                        bingoNumbers.put(coordinate, box);
                    }
                }
                return new Card(id, lobbyId, bingoNumbers,  boxes.size(), boxes.get(0).getAsJsonArray().size());
            }

        };
        gsonBuilder.registerTypeAdapter(Card .class,deserializer);
            Gson customGson = gsonBuilder.create();
            return customGson.fromJson(jsonCard, Card.class);
        }

    public Boolean mapWinGameResult(String isValidJson) throws JSONException {
        JsonObject jsonObject = JsonParser.parseString(isValidJson).getAsJsonObject();
        return jsonObject.get("valide").getAsBoolean();
    }
}
