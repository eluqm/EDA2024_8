class Song{
    int popularity;
    int year;
    double danceability;
    double energy;
    int key;
    double loudness;
    int mode;
    double speechiness;
    double acousticness;
    double instrumentalness;
    double liveness;
    double valance;
    double tempo;
    int time_signature;
    int duration_ms;

    public Song( int popularity, int year, double danceability, double energy, int key, double loudness, int mode, double speechiness, double acousticness, double instrumentalness,
                 double liveness, double valance, double tempo, int time_signature, int duration_ms) {
        this.popularity = popularity;
        this.year = year;
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness; 
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valance = valance;
        this.tempo = tempo;
        this.time_signature = time_signature;
        this.duration_ms = duration_ms;             
    }
}
