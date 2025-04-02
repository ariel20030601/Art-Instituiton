public class AuctionHouse {
    String Location;
    String Name;
    String[] ArtPieces;

    AuctionHouse(String Loc, String N) {
        Location = Loc;
        Name = N;
    };

    public void ExecuteAuction(int Time, String AuctionName) {
        System.out.println("The " + AuctionName + "Auction, " + Name + "'s, in " + Location + "is programmed for " + Time);
    }
}
