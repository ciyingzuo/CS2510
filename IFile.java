// Assignment 4
// partner1-last-name partner1-first-name
// partner1-
// Yingzuo Ci
// ciyingzuo
import tester.*;


//to represent different files in a computer
public interface IFile {
    // compute the size of this file
    int size();
 
    // compute the time (in seconds) to download this file
    // at the given download rate
    int downloadTime(int rate);
 
    // is the owner of this file the same 
    // as the owner of the given file?
    public boolean sameOwner(IFile that);

    // get the owner of the file
    public String getOwner();
}


//to represent a geometric file
abstract class AFile implements IFile {
    String name;
    String owner;
 
    
    // constructor for AFile
    AFile(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }
    
    // is the owner of this file the same 
    // as the owner of the given file?
    public boolean sameOwner(IFile that) {
        return this.owner.equals(that.getOwner());
    }
    
    // get the owner of the file
    public String getOwner() {
        return this.owner;
    }
    
    // compute the size
    public abstract int size();
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    public int downloadTime(int rate) {
        return (this.size() + rate - 1) / rate;
    }
}


//to represent a text file
class TextFile extends AFile {
    int length;   // in bytes

    // Constructor
    TextFile(String name, String owner, int length) {
        super(name, owner);
        this.length = length;
    }
 
    // compute the size of this file
    public int size() {
        return this.length;
    }  
}

//to represent an image file
class ImageFile extends AFile { 
    int width;   // in pixels
    int height;  // in pixels
    
    // Constructor
    ImageFile(String name, String owner, int width, int height) {
        super(name, owner);
        this.width = width;
        this.height = height;
    }
 
    // compute the size of this file
    public int size() {
        return this.width * this.height;
    }  
}


//to represent an audio file
class AudioFile extends AFile {
    int speed;   // in bytes per second
    int length;  // in seconds of recording time
    
        // Constructor
    AudioFile(String name, String owner, int speed, int length) {
        super(name, owner);
        this.speed = speed;
        this.length = length;
    }
 
    // compute the size of this file
    public int size() {
        return this.speed * this.length;
    }  
}

class ExamplesFiles {
 
    IFile text0 = new TextFile("Spanish paper", "John", 2500);
    IFile text1 = new TextFile("English paper", "Maria", 1234);
    IFile picture1 = new ImageFile("Hill", "John", 1000, 500);
    IFile picture = new ImageFile("Beach", "Maria", 400, 200);
    IFile song1 = new AudioFile("Mayday", "Black", 50, 700);
    IFile song = new AudioFile("Help", "Pat", 200, 120);
 
    // test the method size for the classes that represent files
    boolean testSize(Tester t) {
        return
                t.checkExpect(this.text0.size(), 2500) &&
                t.checkExpect(this.text1.size(), 1234) &&
                t.checkExpect(this.picture1.size(), 500000) &&
                t.checkExpect(this.picture.size(), 80000) &&
                t.checkExpect(this.song1.size(), 35000) &&
                t.checkExpect(this.song.size(), 24000);
    }
 
 
    boolean testDownloadTime(Tester t) {
        return
                t.checkExpect(this.text0.downloadTime(1250), 2) &&
                t.checkExpect(this.song1.downloadTime(5000), 7);
     
    }
 
 
    boolean testSameOwner(Tester t) {
        return
                   t.checkExpect(this.text0.sameOwner(this.picture1), true) &&
                   t.checkExpect(this.song.sameOwner(this.text0), false);
    }
}
