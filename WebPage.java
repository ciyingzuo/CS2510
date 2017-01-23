// Assignment 2
// partner1-Ci Yingzuo
// partner1-ciyingzuo

import tester.*;

//represent ILoItem
interface ILoItem {
    int totalImageSize();
    int textLength();
    String combine();
    String images();
}


// represent MtLoItem
class MtLoItem implements ILoItem {
    MtLoItem() {
        //its empty
    }
    
    //compute the total image size
    public int totalImageSize() {
        return 0;
    }
    //computer the text length
    public int textLength() {
        return 0;
    }
    //combine string with another one
    public String combine() {
        return "";
    }
    //determine weather should add ", "
    public String images() {
        return "";
    }
}
//represent IItem
interface IItem {
    int totalImageSize();
    int textLength();
    String combine();
}

//represent a WebPage
public class WebPage {
    String title;
    String url;
    ILoItem items;
    
    //constructor
    WebPage(String title, String url, ILoItem items) {
        this.title = title;
        this.url = url;
        this.items = items;
    }
    //compute the total image size
    public int totalImageSize() {
        return this.items.totalImageSize();
    }
    //computer the text length
    public int textLength() {
        return this.items.textLength() + this.title.length();
    }
    //combine string with another one
    public String combine() {
        return this.items.combine();
    }
    //return the image
    public String images() {
        return this.items.images();
    }
}

// represent text
class Text implements IItem {
    String text;
     
    //constructor
    Text(String text) {
        this.text = text;
    }
    //compute the total image size
    public int totalImageSize() {
        return 0;
    }
    //computer the text length
    public int textLength() {
        return this.text.length();
    }
    //combine string with another one
    public String combine() {
        return "";
    }
}

//represent  Image
class Image implements IItem {
    String fileName;
    int size;
    String fileType;
    
    //constructor
    Image(String fileName, int size, String fileType) {
        this.fileName = fileName;
        this.size = size;
        this.fileType = fileType;
    }
    //compute the total image size
    public int totalImageSize() {
        return this.size;
    }
    //computer the text length
    public int textLength() {
        return this.fileName.length() - 1;
    }
    //combine string with another one
    public String combine() {
        return this.fileName + ", ";
    }
}

//represent Link
class Link implements IItem {
    String name;
    WebPage page;
    
    //constructor
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }
    //compute the total image size
    public int totalImageSize() {
        return this.page.totalImageSize();
    }
    //computer the text length
    public int textLength() {
        return this.name.length() + this.page.textLength();
    }
    //combine string with another one
    public String combine() {
        return this.page.combine();
    }
}

//represent ConsLoItem
class ConsLoItem implements ILoItem {
    IItem first;
    ILoItem rest;

    //constructor
    ConsLoItem(IItem item, ILoItem rest) {
        this.first = item;
        this.rest = rest;
    }
    //compute the total image size
    public int totalImageSize() {
        return this.first.totalImageSize() + this.rest.totalImageSize();
    }
    //computer the text length
    public int textLength() {
        return this.first.textLength() + this.rest.textLength();
    }
    //combine two strings
    public String combine() {
        return this.first.combine() + this.rest.combine();
    }
    //the strings that linked to this page
    public String images() {
        return this.combine().substring(0, (this.combine().length() - 2));
    }
}


//Example about WebPage
//Because HtDP is link to fundiesII
class ExamplesWebPage {
    WebPage HtDP = new WebPage("HtDP", "htdp.org",
            new ConsLoItem(new Text("How to Design Programs"),
            new ConsLoItem(new Image("htdp.tiff", 4300, "tiff"),
                    new MtLoItem())));
    
    WebPage OOD = new WebPage("OOD", "ccs.neu.edu/OOD",
            new ConsLoItem(new Text("Stay classy, Java"),
            new ConsLoItem(new Link("Back to the Future", this.HtDP),
                    new MtLoItem())));
 
    WebPage fundiesWP = new WebPage("Fundies II", "ccs.neu.edu/Fundies2",
            new ConsLoItem(new Text("Home sweet home"),
                    new ConsLoItem(new Image("wvh-lab.png", 400, "png"),
                            new ConsLoItem(new Text("The Staff"),
                                    new ConsLoItem(new Image("profs.jpeg", 240,
                                            "jpeg"),
                                            new ConsLoItem(new Link(
                                                    "A Look Back", this.HtDP),
                                                    new ConsLoItem(new Link(
                                                            "A Look Ahead",
                                                            this.OOD),
                                                            new MtLoItem())))))));
    
    // test the method
    boolean testTotalImageSize(Tester t) { 
        return t.checkExpect(this.fundiesWP.totalImageSize(), 9240);
    }
    boolean testTextLength(Tester t) {
        return t.checkExpect(this.fundiesWP.textLength(), 182);
    }
    boolean testCombine(Tester t) { 
        return t.checkExpect(this.fundiesWP.images(), 
                "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff");
    }
    boolean testTotalImageSize1(Tester t) { 
        return t.checkExpect(this.HtDP.totalImageSize(), 4300);
    }
    boolean testTextLength1(Tester t) {
        return t.checkExpect(this.OOD.textLength(), 72);
    }
    boolean testCombine1(Tester t) { 
        return t.checkExpect(this.HtDP.images(), 
                "htdp.tiff");
    }
}