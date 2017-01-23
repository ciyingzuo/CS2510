import tester.*;

//htdp.tiff appears twice since the function recurrs through any links
//in the List of Item and outputs its image names.
interface ILoItem { 
    int totalImageSize(); 
    int textLength();
    String images();
    boolean sameLoItem(ILoItem that);
    boolean sameMtLoItem(MtLoItem that);
    boolean sameConsLoItem(ConsLoItem that);
}

interface IItem { 
    int imageSize();
    int textLength();
    String imageName();
    boolean sameItem(IItem that);
}

// To represent a web page 
class WebPage {
    String url;
    String title;
    ILoItem items;
    
    WebPage(String url, String title, ILoItem items) {
        this.url = url;
        this.title = title;
        this.items = items; 
    }
    /* TEMPLATE
     * FIELDS
     * this.url                    ... --String
     * this.title                  ... --String
     * this.items                  ... --ILoItem
     * 
     * METHODS
     * this.textLength()           ... --int
     * this.totalImageSize()       ... --int
     * this.images()               ... --String
     * this.items.totalImageSize() ... --int
     * this.items.textLength()     ... --int
     * this.items.images()         ... --String
     *
     */
    
    // compute the total number of characters in a webpage 
    public int textLength() {
        return this.title.length() + this.items.textLength();
    }
    
    // compute the total image size in this web page
    public int totalImageSize() {
        return this.items.totalImageSize();
    }
    
    // outputs a list of image names in the webpage
    public String images() {
        return this.items.images();
    }
    
    // checks if two webpages are the same
    public boolean sameWebPage(WebPage that)
    {
        return this.url.equals(that.url)
                && this.title.equals(that.title)
                && this.items.sameLoItem(that.items);
    }
}

// To represent Text
class Text implements IItem {
    String contents; 
    
    Text(String contents) {
        this.contents = contents; 
    }
    /* TEMPLATE
     * FIELDS
     * contents          ... --String
     * 
     * METHODS
     * this.imageSize()  ... --String
     * this.textLength() ... --int
     * this.imageName()  ... --String
     */
    public int imageSize() {
        return 0;
    }
    
    //determine if the item is text
    public boolean isText() {
        return true;
    }
    
    // compute the total number of characters in this Text
    public int textLength() {
        return this.contents.length();
    }
    
    // output the name of the image
    public String imageName() {
        return "";
    }
    
    // methods and helpers to test for sameness between IItems
    public boolean sameItem(IItem that) {
        if (that instanceof Text) {
            if (that instanceof Header) {
                return ((Header)that).sameItem(this);
            }
            else if (that instanceof Paragraph) {
                return ((Paragraph)that).sameItem(this);
            }
            else {
                return this.sameText((Text) that);
            }
        }
        else {
            return false;
        }
    }
    public boolean sameText(Text that) {
        return this.contents.equals(that.contents);
    }
    
}
// Header and Paragraph files
class Header extends Text {
    Header(String contents) {
        super(contents);
    }
    public boolean sameItem(IItem that) {
        if (that instanceof Header) {
            return ((Header) that).sameHeader(this);
        }
        else {
            return false;
        }
    }
    public boolean sameHeader(Header that) {
        return this.contents.equals(that.contents);
    }
    
    // determine if the item is header
    public boolean isHeader() {
        return true;
    }
}

// represent paragraph
class Paragraph extends Text {
    Paragraph(String contents) {
        super(contents);
    }
    public boolean sameItem(IItem that) {
        if (that instanceof Paragraph) {
            return ((Paragraph) that).sameParagraph(this);
        }
        else {
            return false;
        }
    }
    public boolean sameParagraph(Paragraph that) {
        return this.contents.equals(that.contents); 
    }
    
    // determine if the item is paragraph
    public boolean isParagraph() {
        return true;
    }
}
// To represent an Image
class Image implements IItem {
    String fileName;
    int size;
    String fileType;
    
    Image(String fileName, int size, String fileType) {
        this.fileName = fileName; 
        this.size = size;
        this.fileType = fileType; 
    }
    /* TEMPLATE
     * FIELDS
     * this.fileName     ... --String
     * this.size         ... --int
     * this.fileType     ... --String
     * 
     * METHODS
     * this.imageSize()  ... --int
     * this.textLength() ... --int
     * this.imageName()  ... --String
     */
    public int imageSize() {
        return this.size; 
    }
    
    // compute the total number of characters in this.fileName and this.fileType
    public int textLength() {
        return this.fileName.length() + this.fileType.length();
    }
    
 // output the name of the image
    public String imageName() {
        return this.fileName + "." + this.fileType;
    }
    
    // methods and helpers to test for sameness between IItems
    public boolean sameItem(IItem that) {
        if (that instanceof Image) {
            return this.sameImage((Image) that);
        }
        else {
            return false;
        }
    }
    public boolean sameImage(Image that) {
        return this.fileName.equals(that.fileName) &&
                this.size == that.size &&
                this.fileType.equals(that.fileType);
    }
}

// To represent a link to a website 
class Link implements IItem {
    String name;
    WebPage page;
    
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }
    
    /* TEMPLATE
     * FIELDS
     * this.name         ... --String
     * this.page         ... --WebPage
     * 
     * METHODS
     * this.imageSize()  ... --int
     * this.textLength() ... --int
     * this.imageName()  ... --String
     */
    
    // compute the total image size for the web page
    public int imageSize() {
        return page.totalImageSize();
    }
    
    // compute the total number of characters in this link; 
    public int textLength() {
        return this.name.length() + this.page.textLength();
    }
    
    // output the name of the image
    public String imageName() {
        return this.page.images();
    }
    // methods and helpers to test for sameness between IItems
    public boolean sameItem(IItem that) {
        if (that instanceof Link) {
            return this.sameLink((Link) that);
        }
        else {
            return false;
        }
    }
    public boolean sameLink(Link that) {
        return this.name.equals(that.name) &&
                this.page.sameWebPage(that.page);
    }
}

// to represent a Linked List of Items 
class ConsLoItem implements ILoItem {
    IItem first; 
    ILoItem rest; 
    
    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }
    /* Template
     * FIELDS:
     * this.first            ... --Item
     * this.rest             ... --ILoItem
     * METHODS:
     * this.totalImageSize() ... --int
     * this.imageSize()      ... --int
     * this.textLength()     ... --int
     * this.imageName()      ... --int
     * this.images()         ... --String
     *
     */
    // compute the total image size in this web page
    public int totalImageSize() {
        return this.first.imageSize() + this.rest.totalImageSize();
    }
    
    // compute the total text length in a List of Items
    public int textLength() {
        return this.first.textLength() + this.rest.textLength(); 
    }
    
    // output the name of the image
    public String images() {
        if (this.first.imageName().equals("") || this.rest.images().equals("")) {
            return this.first.imageName() + this.rest.images();
        }
        else { 
            return this.first.imageName() + ", " + this.rest.images();
        }
    }
    
    // methods to check for sameness
    public boolean sameLoItem(ILoItem that) {
        return that.sameConsLoItem(this);
    }
    public boolean sameConsLoItem(ConsLoItem that) {
        return this.first.sameItem(that.first) &&
                this.rest.sameLoItem(that.rest);
    }
    public boolean sameMtLoItem(MtLoItem that) {
        return false;
    }
}
// to represent a null pointer 
class MtLoItem implements ILoItem {
    
    /* TEMPLATE
     * METHODS
     * this.totalImageSize() ... --int
     * this.textLength()     ... --int
     * this.images()         ... --String
     */
    
    // to compute the total length of images in a List of Item
    public int totalImageSize() {
        return 0; 
    }
    
    // to compute the total lenght of text in a List of Item 
    public int textLength() {
        return 0;
    }
    
    // output the name of the image
    public String images() {
        return "";
    }
    
    // methods for sameness
    public boolean sameLoItem(ILoItem that) {
        return that.sameMtLoItem(this);
    }
    public boolean sameConsLoItem(ConsLoItem that) {
        return false;
    }
    public boolean sameMtLoItem(MtLoItem that) {
        return true;
    }
}

class ExamplesWebPage {
    WebPage htdp = new WebPage("htdp.org", "HtDP", 
            new ConsLoItem(new Text("How to Design Programs"),
                    new ConsLoItem(new Image("htdp", 4300, "tiff"),
                            new MtLoItem())));
    
    WebPage ood = new WebPage("ccs.neu.edu/ood", "OOD",
            new ConsLoItem(new Text("Stay classy, Java"),
                    new ConsLoItem(new Link("Back to the future", htdp), 
                            new MtLoItem())));
    
    WebPage fundiesWP = new WebPage("ccs.neu.edu/Fundies2", "Fundies II",  
             new ConsLoItem(new Text("Home sweet home"), 
                     new ConsLoItem(new Image("wvh-lab", 400, "png"),
                             new ConsLoItem(new Text("the staff"),
                                     new ConsLoItem(new Image("profs", 240, "jpeg"),
                                             new ConsLoItem(new Link("A Look Back", htdp),
                                                     new ConsLoItem(new Link("A Look Ahead", ood), 
                                                             new MtLoItem())))))));

    WebPage anotherWP = new WebPage("https://www.website.com"
            , "The Website!"
            , new ConsLoItem(new Text("Websiteeeeeeeeeeeeeeeeeeeeeeee")
                , (new ConsLoItem(new Image("potato", 2, "jpeg")
                    , (new ConsLoItem(new Image("tomato", 3, "gif")
                        , (new ConsLoItem(new Link("Click here"
                                                  , new WebPage("www.theyrewatching.com"
                                                                , "RUN"
                                                                , new MtLoItem()))
                            , (new ConsLoItem(new Link("Don't click here"
                                                       , new WebPage("www.bankofamerica.com"
                                                                     , "You are now robbed"
                                                                     , new MtLoItem()))
                                , (new ConsLoItem(new Link("cool stuff"
                                                          , new WebPage("http://www.elgoog.im"
                                                                       , ".enigne hcraes ehT"
                                                                       , new MtLoItem()))
                                                          , new MtLoItem()))))))))))));
    WebPage wp1 = new WebPage("https://www.google.com"
            , "The Search Engine of the Internet", new MtLoItem());
    WebPage wp2 = new WebPage("https://www.google.com"
            , "The completely real google.com, totally not a spoof"
            , new MtLoItem());
    WebPage wp3 = new WebPage("https://www.google.com"
            , "The Search Engine of the Internet"
            , new ConsLoItem(new Link("Click here"
                                     , new WebPage("www.virus.com"
                                                  , "a virus"
                                                  , new MtLoItem()))
                            , new MtLoItem()));
    IItem h1 = new Header("header1");
    IItem h2 = new Header("paragraph1");
    IItem h3 = new Header("header1");
    IItem p1 = new Paragraph("paragraph1");
    IItem p2 = new Paragraph("header1");
    IItem p3 = new Paragraph("paragraph1");
    IItem txt1 = new Text("txt1");
    IItem txt2 = new Text("txt2");
    IItem txt3 = new Text("txt1");
    IItem txt4 = new Text("header1");
    
    IItem im1 = new Image("crocodile", 56, "png");
    IItem im2 = new Image("crocodile", 52, "png");
    IItem im3 = new Image("crocodile", 56, "wmv");
    IItem im4 = new Image("crocodile", 56, "png");
    IItem im5 = new Image("croc", 56, "png");
    
    Link ln1 = new Link("link1", wp1);
    Link ln2 = new Link("link2", wp1);
    Link ln3 = new Link("link1", wp2);
    Link ln4 = new Link("link1", wp1);
    
    ILoItem l1 = new ConsLoItem(h1, new ConsLoItem(h2, new MtLoItem()));
    ILoItem l2 = new MtLoItem();
    ILoItem l3 = new ConsLoItem(h1, new ConsLoItem(h2, new MtLoItem()));
    ILoItem l4 = new ConsLoItem(h1, new ConsLoItem(h2, new ConsLoItem(h3, new MtLoItem())));
    ILoItem l5 = new ConsLoItem(h1, new ConsLoItem(h3, new MtLoItem()));

    boolean testTextLength(Tester t) {
        return t.checkExpect(fundiesWP.textLength(), 182)
                && t.checkExpect(anotherWP.textLength(), 136);
    }
    
    boolean testImageSize(Tester t) {
        return t.checkExpect(fundiesWP.totalImageSize(), 9240)
                && t.checkExpect(anotherWP.totalImageSize(), 5);
    }
    
    boolean testImages(Tester t) {
        return t.checkExpect(fundiesWP.images(), "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff")
                && t.checkExpect(anotherWP.images(), "potato.jpeg, tomato.gif");
    }
    boolean testSameWebPage(Tester t) {
        return t.checkExpect(anotherWP.sameWebPage(anotherWP), true)
                && t.checkExpect(fundiesWP.sameWebPage(fundiesWP), true)
                && t.checkExpect(ood.sameWebPage(fundiesWP), false)
                && t.checkExpect(wp1.sameWebPage(wp2), false)
                && t.checkExpect(wp1.sameWebPage(wp3), false);
    }
    boolean testSameHeader(Tester t) {
        return t.checkExpect(((Header)h1).sameHeader((Header)h3), true)
                && t.checkExpect(((Header)h1).sameHeader((Header)h2), false)
                && t.checkExpect(((Header)h1).sameHeader((Header)h1), true)
                && t.checkExpect(((Header)h3).sameHeader((Header)h1), true);
    }
    boolean testSameText(Tester t) {
        return t.checkExpect(((Text)h1).sameText((Text)h3), true)
                && t.checkExpect(((Text)h1).sameText((Text)h2), false)
                && t.checkExpect(((Text)p1).sameText((Text)p3), true)
                && t.checkExpect(((Text)p3).sameText((Text)p1), true)
                && t.checkExpect(((Text)p1).sameText((Text)p2), false)
                && t.checkExpect(((Text)h2).sameText((Text)p1), true)
                && t.checkExpect(((Text)p2).sameText((Text)h1), true)
                && t.checkExpect(((Text)txt1).sameText((Text)txt1), true)
                && t.checkExpect(((Text)txt1).sameText((Text)txt2), false)
                && t.checkExpect(((Text)txt1).sameText((Text)txt3), true)
                && t.checkExpect(((Text)txt3).sameText((Text)txt1), true)
                && t.checkExpect(((Text)txt4).sameText((Text)h1), true)
                && t.checkExpect(((Text)h1).sameText((Text)txt4), true);
    }
    boolean testSameImage(Tester t) {
        return t.checkExpect(((Image)im1).sameImage((Image)im1), true)
                && t.checkExpect(((Image)im1).sameImage((Image)im2), false)
                && t.checkExpect(((Image)im1).sameImage((Image)im3), false)
                && t.checkExpect(((Image)im1).sameImage((Image)im4), true)
                && t.checkExpect(((Image)im1).sameImage((Image)im5), false)
                && t.checkExpect(((Image)im4).sameImage((Image)im1), true);
    }
    boolean testSameLink(Tester t) {
        return t.checkExpect(((Link)ln1).sameLink((Link)ln1), true)
                && t.checkExpect(((Link)ln1).sameLink((Link)ln2), false)
                && t.checkExpect(((Link)ln1).sameLink((Link)ln3), false)
                && t.checkExpect(((Link)ln1).sameLink((Link)ln4), true)
                && t.checkExpect(((Link)ln4).sameLink((Link)ln1), true);
    }
    boolean testSameLoItem(Tester t) {
        return t.checkExpect(l1.sameLoItem(l1), true)
                && t.checkExpect(l1.sameLoItem(l2), false)
                && t.checkExpect(l1.sameLoItem(l3), true)
                && t.checkExpect(l1.sameLoItem(l4), false)
                && t.checkExpect(l1.sameLoItem(l5), false);
    }
    boolean testSameItem(Tester t) {
        return t.checkExpect(h1.sameItem(h3), true)
                && t.checkExpect(h1.sameItem(h2), false)
                && t.checkExpect(p1.sameItem(p3), true)
                && t.checkExpect(p3.sameItem(p1), true)
                && t.checkExpect(p1.sameItem(p2), false)
                && t.checkExpect(h2.sameItem(p1), false)
                && t.checkExpect(p2.sameItem(h1), false)
                && t.checkExpect(txt1.sameItem(txt1), true)
                && t.checkExpect(txt1.sameItem(txt2), false)
                && t.checkExpect(txt1.sameItem(txt3), true)
                && t.checkExpect(txt3.sameItem(txt1), true)
                && t.checkExpect(txt4.sameItem(h1), false)
                && t.checkExpect(h1.sameItem(txt4), false)
                && t.checkExpect(ln1.sameItem(ln1), true)
                && t.checkExpect(ln1.sameItem(ln2), false)
                && t.checkExpect(ln1.sameItem(ln3), false)
                && t.checkExpect(ln1.sameItem(ln4), true)
                && t.checkExpect(ln4.sameItem(ln1), true)
                && t.checkExpect(ln1.sameItem(p1), false)
                && t.checkExpect(ln1.sameItem(h1), false)
                && t.checkExpect(p1.sameItem(ln1), false)
                && t.checkExpect(h1.sameItem(ln1), false)
                && t.checkExpect(im1.sameItem(im1), true)
                && t.checkExpect(im1.sameItem(im2), false)
                && t.checkExpect(im1.sameItem(im3), false)
                && t.checkExpect(im1.sameItem(im4), true)
                && t.checkExpect(im1.sameItem(im5), false)
                && t.checkExpect(im4.sameItem(im1), true);
    }
}
