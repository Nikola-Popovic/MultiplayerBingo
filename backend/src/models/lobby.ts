import Joueur from "./joueur";
import * as database from '../database'
import GeoLocation from "./geolocation";
import Util from "../util";
import shuffle from 'lodash.shuffle';
import { sendNextBouleToLobby, sendGameOverToLobby, unSubscribeTokenToLobbyTopic } from "../messaging";

export default class Lobby{
    private _joueurs : Joueur[];
    private _host : Joueur;
    private _geolocation : GeoLocation;

    readonly id : number;
    readonly nom : string;
    private _boulesPiges : string[];
    private _estCommencee : boolean;
    private _nextBoules : string[];
    private _terminee : boolean;
    static nextId : number = 0;

    constructor(host : Joueur, name : string, geolocation: GeoLocation){
        this._host = host;
        this.nom = name;
        this._geolocation = geolocation;
        database.addLobby(this);
        this.id = Lobby.nextId++;
        this._boulesPiges = [];
        this._nextBoules = this.generateRandomNextBoules();
        this._joueurs = [];
        this._joueurs.push(this._host);
        host.assignerALobby(this);
        this._estCommencee = false;
        this._terminee = false;
    }

    private generateRandomNextBoules() : string[] {
        // Generate number array from 1 to 75
        const numbers = Array.from({length: 75}, (_, i) => i + 1);

        const numbersWithLetters = numbers.map(number => {
            if (1 <= number && number <= 15) {
                return "B" + number;
            } else if (16 <= number && number <= 30) {
                return "I" + number;
            } else if (31 <= number && number <=45) {
                return "N" + number;
            } else if (46 <= number && number <= 60) {
                return "G" + number;
            } else {
                return "O" + number;
            }
        })

        // Shuffle the numbers to randomize which numbers to send to the players
        return shuffle(numbersWithLetters);
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
        const timer = setInterval(() => {
            if (this._terminee) {
                const tokens = this.joueurs.map(joueur => joueur.token);
                this.joueurs.forEach(joueur => joueur.resetLobby());
                unSubscribeTokenToLobbyTopic(tokens, this.id);
                clearInterval(timer);
                console.log('clearInterval');
            } else {
                const nextBoule = this.getNextBoule();
                
                if (nextBoule !== null) {
                    console.log(nextBoule);
                    sendNextBouleToLobby(nextBoule, this.id);
                } else {
                    this.stopGame();
                    sendGameOverToLobby(this.id);
                }
            }
        }, 30 * 1000);
    }

    stopGame() {
        this._terminee = true;
    }

    selectNewRandomHost() {
        const ranHostIndex = Math.floor(Math.random() * this._joueurs.length);
        this._host = this._joueurs[ranHostIndex];
    }

    givenNumbersWereDrawn(numberArray: string[]) {
        for (let i = 0; i < numberArray.length; i ++){
            if (!this._boulesPiges.includes(numberArray[i])) {
                return false;
            }
        }
        return true;
    }

    isInAcceptableDistance(location: GeoLocation): boolean {
        return this._geolocation.distanceToLocation(location) / 1000 < Util.MAX_LOBBY_DISTANCE
    }

    private getNextBoule() : string {
        if (this._nextBoules.length !== 0) {   
            const nextNumber = this._nextBoules.pop();
            this._boulesPiges.push(nextNumber);
            return nextNumber;
        } else {
            return null; // no more numbers
        }
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
