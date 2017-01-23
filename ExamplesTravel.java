// Assignment 1
// Ci Yingzuo
// ciyingzuo

// represent Habitation
interface IHabitation {

}

// to represent Planet
class Planet implements IHabitation {
    String name;
    int population;
    int spaceports;
 
// constructor for Planet
    Planet(String name, int population, int spaceports) {
        this.name = name;
        this.population = population;
        this.spaceports = spaceports;
    }
}


// to represent SpaceStation
class SpaceStation implements IHabitation {
    String name;
    int population;
    int transporterPads;
    
// constructor for SpaceStation
    SpaceStation(String name, int population, int transporterPads) {
        this.name = name;
        this.population = population;
        this.transporterPads = transporterPads;
    }
}

interface ITransportation {

}


// to represent Transporter
class Transporter implements ITransportation {
    IHabitation from;
    IHabitation to;

// constructor for Transporter
    Transporter(IHabitation from, IHabitation to) {
        this.from = from;
        this.to = to;
    }
}


// to represent Shuttle
class Shuttle implements ITransportation {
    IHabitation from;
    IHabitation to;
    int fuel;
    int capacity;

// constructor for Shuttle
    Shuttle(IHabitation from, IHabitation to, int fuel, int capacity) {
        this.from = from;
        this.to = to;
        this.fuel = fuel;
        this.capacity = capacity;
    }
}


// to represent SpaceElecator
class SpaceElevator implements ITransportation {
    IHabitation from;
    IHabitation to;
    int tonnage;

// constructor for SpaceEvevator
    SpaceElevator(IHabitation from, IHabitation to, int tonnage) {
        this.from = from;
        this.to = to;
        this.tonnage = tonnage;
    }
}

// examples for travel
class ExamplesTravel {
    IHabitation nausicant = new Planet("Nausicant", 6000000, 8);
    IHabitation earth = new Planet("Earth", 9000000, 14);
    IHabitation remus = new Planet("Remus", 18000000, 23);
    IHabitation watcherGrid = new SpaceStation("WatcherGrid", 1, 0);
    IHabitation deepSpace42 = new SpaceStation("Deep Space 42", 7, 8);
    IHabitation reach = new Planet("Reach", 15000000, 50);
    
    ITransportation transporter1 = new Transporter(nausicant, remus);
    ITransportation transporter2 = new Transporter(earth, reach);
    ITransportation shuttle1 = new Shuttle(remus, watcherGrid, 3000, 5000000);
    ITransportation shuttle2 = new Shuttle(watcherGrid, earth, 200, 1000000);
    ITransportation elevator1 = new SpaceElevator(deepSpace42, remus, 500);
    ITransportation elevator2 = new SpaceElevator(remus, deepSpace42, 500);

}