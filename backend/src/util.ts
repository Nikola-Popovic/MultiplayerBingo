export default class Util{
    public static TAILLE_BINGO : number = 5;
    public static LIMIT_PAR_COLONNE : number = 15;
    public static MAX_LOBBY_DISTANCE : number = 10; // in km

    public static distance(lon1: number, lat1: number, lon2:number, lat2: number): number {
        const R = 6371e3; // metres
        const theta1 = lat1 * Math.PI/180;
        const theta2 = lat2 * Math.PI/180;
        const deltaTheta = (lat2-lat1) * Math.PI/180;
        const deltaLambda = (lon2-lon1) * Math.PI/180;

        const a = Math.sin(deltaTheta/2) * Math.sin(deltaTheta/2) +
            Math.cos(theta1) * Math.cos(theta2) *
            Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c; // in metres
    }
}
