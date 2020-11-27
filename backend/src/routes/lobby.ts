import express from 'express'
const router = express.Router();
import * as database from '../database'
import Carte from "../models/carte";
import Lobby from "../models/lobby";
import GeoLocation from "../models/geolocation";

// Obtenir toutes les parties
router.get("/", (req : any, res : any, next : any) => {
  const lobbies : any[] = [];
  for(const lobby of database.lobbies.filter(l => !l.estCommencee)){
    lobbies.push(lobby.toJSON());
  }
  res.send({
    "lobbies" : lobbies
  });
})

router.post("/", (req : any, res : any, next : any) => {
  const lobbies : any[] = [];
  const longitude = req.body.longitude;
  const latitude = req.body.latitude;

  if (!longitude || !latitude) {
    res.status(400);
    res.send("Veuillez entrer la longitude et latitude courante.");
  }

  const geoLocation = new GeoLocation(longitude, latitude);

  for(const lobby of database.lobbies.filter(l => !l.estCommencee)){
    if (lobby.isInAcceptableDistance(geoLocation)) {
      lobbies.push(lobby.toJSON());
    }
  }

  res.send({
    "lobbies" : lobbies
  });
});

// Créer une partie
router.post("/", (req : any, res : any, next : any) => {
  const host = database.getJoueurById(parseInt(req.body.hostId, 10));
  const lobbyName = req.body.nom;
  const longitude = req.body.longitude;
  const latitude = req.body.latitude;

  if(lobbyName === "" || lobbyName === undefined){
    res.status(400);
    res.send("Veuillez envoyer un nom de lobby.");
  }

  if (!longitude || !latitude) {
    res.status(400);
    res.send("Veuillez entrer la longitude et latitude courante.");
  }

  else {
    if (host != null) {
      const lobby = new Lobby(host, lobbyName, new GeoLocation(longitude, latitude));
      res.send({
        "lobby": lobby.toJSON()
      });
    } else {
      res.status(400);
      res.send("Le hostId ne correspond pas a un joueur connu.");
    }
  }
})

// Obtenir une partie par ID
router.get("/:id", (req : any, res : any, next : any) => {
  const lobby = database.getLobbyById(parseInt(req.params.id, 10));
  if(lobby !== undefined) {
    res.send({
      "lobby": lobby.toJSON()
    });
  }
  else{
    res.status(400);
    res.send("Le id recu ne correspond pas a un lobby connu.");
  }
})

// Créer une carte pour une partie
router.get("/carte/:id", (req : any, res : any, next : any) => {
  const id = parseInt(req.params.id, 10);
  if(database.lobbyExiste(id)){
    const carte = new Carte(id);
    res.send({
      "carte" : carte.toJSON()
    });
  }
  else{
    res.status(400);
    res.send("Le id recu ne correspond pas a un lobby connu.");
  }
});

// Ajouter un joueur à une partie
router.put("/:id/user", (req : any, res : any, next : any) => {
  let erreur = ""
  const joueur = database.getJoueurById(parseInt(req.body.joueurId, 10));
  if(joueur != null){
    const lobby = database.getLobbyById(parseInt(req.params.id, 10));
    if(lobby != null) {
      if (joueur.lobby === null) {
        lobby.addToLobby(joueur);
        database.saveLobby(lobby);
        joueur.assignerALobby(lobby);
        res.send({
          "success": true
        })
      } else {
        erreur = "Le joueur recu est deja inscrit dans un lobby.";
      }
    }
    else {
      erreur = "Le lobby recu ne correspond pas a un lobby connu.";
    }
  }
  else {
    erreur = "Le joueurId recu ne correspond pas a un joueur connu.";
  }
  if(erreur != ""){
    res.status(400);
    res.send(erreur);
  }
})

// Enlever un joueur d'une partie
router.delete("/:id/joueur", (req : any, res : any, next : any) => {
  let erreur = "";
  const joueur = database.getJoueurById(parseInt(req.body.joueurId, 10));
  if(joueur != null){
    const lobby = database.getLobbyById(parseInt(req.params.id, 10));
    if(lobby != null){
      if(joueur.lobby?.equals(lobby)) {
        lobby.removeFromLobby(joueur);
        joueur.quitterPartie();

        if (lobby.joueurs.length === 0) {
          database.deleteLobby(lobby);
        } else if (lobby.host.id === joueur.id){
          // The host Quit
          lobby.selectNewRandomHost();
          database.saveLobby(lobby);
        } else {
          database.saveLobby(lobby);
        }
        res.send({
          "success": true
        })
      }
      else {
        erreur = "Le joueur n'est pas inscrit au lobby recu, nous ne pouvons donc pas le desinscrire.";
      }
    }
    else {
      erreur = "Le lobby recu ne correspond pas a un lobby connu."
    }
  }
  else {
    erreur = "Le joueurId recu ne correspond pas a un joueur connu.";
  }
  if(erreur !== ""){
    res.status(400);
    res.send(erreur);
  }
})

router.post("/:id/win", (req : any, res : any, next : any) => {
  let erreur = "";
  const lobby = database.getLobbyById(parseInt(req.params.id, 10));
  if(lobby !== undefined){
    const carte = database.getCarteById(parseInt(req.body.carteId, 10));
    const numeroGagnants = req.body.numeros;
    if(carte !== undefined){
      const valide = lobby.givenNumbersWereDrawn(numeroGagnants) && carte.valider(lobby.id, numeroGagnants);
      res.send({
        "valide" : valide
      });
    }
    else {
      erreur += "La carte n'existe pas\n"
    }
  }
  else{
    erreur += "Le lobby n'existe pas\n";
  }
  if(erreur !== ""){
    res.status(400);
    res.send(erreur);
  }
});

export default router;
