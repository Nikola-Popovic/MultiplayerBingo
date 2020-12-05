import firebase from './firebase-config';
import Carte from './models/carte';

export function sendCardToTokens(tokens : string[], lobbyId : number) {
  tokens.forEach(token => {
    firebase.messaging().sendToDevice(token, {
      data: {
        "type": "carte",
        "payload": JSON.stringify(new Carte(lobbyId).toJSON())
      }
    });
  });
}
