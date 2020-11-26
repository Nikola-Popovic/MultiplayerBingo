import Joueur from "./joueur";
import * as database from '../database'

export default class Lobby{
    private _joueurs : Joueur[];
    private _host : Joueur;
    readonly id : number;
    readonly nom : string;
    readonly chiffrePiges : number[];
    private _estCommencee : boolean;
    static nextId : number = 0;

    constructor(host : Joueur, name : string){
        this._host = host;
        this.nom = name;
        database.addLobby(this);
        this.id = Lobby.nextId++;
        this.chiffrePiges = [];
        this._joueurs = [];
        this._joueurs.push(this._host);
        host.assignerALobby(this);
        this._estCommencee = false;
    }

    addToLobby(joueur : Joueur){
        this._joueurs.push(joueur);
    }

    removeFromLobby(joueur : Joueur){
        this._joueurs = this._joueurs.filter(j => j.id !== joueur.id);
    }

    get joueurs(){
        return this._joueurs;
    }

    get host() {
        return this._host;
    }

    get estCommencee() {
        return this._estCommencee;
    }

    startGame() {
        this._estCommencee = true;
    }

    selectNewRandomHost() {
        const ranHostIndex = Math.floor(Math.random() * this._joueurs.length);
        this._host = this._joueurs[ranHostIndex];
    }

    givenNumbersWereDrawn(numberArray: number[]) {
        for (let i = 0; i < numberArray.length; i ++){
            if (!this.chiffrePiges.includes(numberArray[i])) {
                return false;
            }
        }
        return true;
    }

    toJSON(){
        const participants : object[] = [];
        for(const joueur of this.joueurs){
            participants.push(joueur.toJSON());
        }
        return {
            "id" : this.id,
            "nom" : this.nom,
            "host" : this._host.toJSON(),
            "participants" : participants
        }
    }

    equals(lobby : Lobby){
        return this.id === lobby.id;
    }

}
