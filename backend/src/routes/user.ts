import express from 'express'
const router = express.Router();
import Joueur from "../models/joueur";

router.post("/", (req : any, res : any, next : any) => {
    let nomJoueur = req.body.username;
    if(nomJoueur === "" || nomJoueur === undefined){
      res.status(400);
      res.send("Veuillez envoyer un username.");
    }
    else {
      const joueur = new Joueur(nomJoueur);
      res.send(joueur.toJSON());
    }
  })

export default router;
