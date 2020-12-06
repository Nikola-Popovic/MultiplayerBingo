import firebase from './firebase-config';
import Carte from './models/carte';
import Joueur from './models/joueur';

export function sendCardToTokens(tokens : string[], lobbyId : number) {
  tokens.forEach(token => {
    firebase.messaging().sendToDevice(token, {
      data: formatMessage("carte", JSON.stringify(new Carte(lobbyId).toJSON()))
    });
  });
}

export function subscribeTokenToLobbyTopic(token : string, lobbyId : number) {
  firebase.messaging().subscribeToTopic(token, getLobbyTopic(lobbyId));
}

export function unSubscribeTokenToLobbyTopic(token : string | string[], lobbyId : number) {
  firebase.messaging().unsubscribeFromTopic(token, getLobbyTopic(lobbyId));
}

export function sendAddedPlayerMessageToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("addedPlayer", JSON.stringify(joueur.toJSON()))
  });
}

export function sendRemovedPlayerMessageToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("removedPlayer", JSON.stringify(joueur.toJSON()))
  });
}

export function sendNextBouleToLobby(nextBoule : string, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("nextBoule", nextBoule)
  });
}

export function sendWinnerToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("winner", JSON.stringify(joueur.toJSON()))
  });
}

export function sendGameOverToLobby(lobbyId : number) { 
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("gameOver", "")
  });
}

function getLobbyTopic(lobbyId : number) {
  return `lobby${lobbyId}Topic`;
}

function formatMessage(type : string, payload : string) : { "type" : string, "payload" : string} {
  return {
    "type": type,
    "payload": payload
  }
}
