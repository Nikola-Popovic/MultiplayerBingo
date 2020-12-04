import express from 'express'
const router = express.Router();
import Joueur from "../models/joueur";

router.post("/", (req : any, res : any, next : any) => {
    let nomJoueur = req.body.username;
    let token = req.body.token;
    if(nomJoueur === "" || nomJoueur === undefined){
      res.status(400);
      res.send("Veuillez envoyer un username.");
    }
    if(token === "" || token === undefined) {
      res.status(400);
      res.send("Veuillez envoyer un token.");
    }
    else {
      const joueur = new Joueur(nomJoueur, token);
      res.send(joueur.toJSON());
    }
  })

export default router;
