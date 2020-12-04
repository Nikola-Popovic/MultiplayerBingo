import * as database from '../database'
import Lobby from "./lobby";

export default class Joueur{
    readonly id : number;
    private _lobby : Lobby;
    readonly username : string;
    static nextId : number = 0;

    constructor(username : string) {
        database.addJoueur(this);
        this.id = Joueur.nextId++;
        this._lobby = null;
        this.username = username;
    }

    assignerALobby(lobby : Lobby){
        this._lobby = lobby;
    }

    quitterPartie(){
        this._lobby = null;
    }

    get lobby(){
        return this._lobby;
    }

    toJSON(){
        return {
            "id" : this.id,
            "username" : this.username
        }
    }
}
