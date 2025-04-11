package core;

public class Institution {
    static int MoverForObj = 0;
    static int MoverForAuc = 0;
    String[] ObjectsOfInterest;
    AuctionHouse[] AuctionHouses;
    Boolean viewable = false;


    Institution() {
        ObjectsOfInterest = new String[100];
        AuctionHouses = new AuctionHouse[100];
    };

    public void BuyOoI(String ArtPiece, String AuctionPlace) {
        ObjectsOfInterest[MoverForObj] = ArtPiece;
        MoverForObj++;
        System.out.println("Insitituion has obtained the OI " + ArtPiece + "from auction house " + AuctionPlace);
    }

    protected void MakeViewable() {
        viewable = true;
    }

    protected void MakePrivate() {
        viewable = false;
    }

    public void ViewObjects(User user) {

    if(MoverForObj != 0) {
        if (viewable == true || user.getRole() == "Administrator") {
            System.out.println("All current objects of interest offered by the insitituiton:"); 
            for(int i = 0; i <= MoverForObj; i++) {
                System.out.println(i + ". " + ObjectsOfInterest[i]);
            };
        } else {
            System.out.println("Objects of interest are not currently viewable");
        }
    }
    }
}
